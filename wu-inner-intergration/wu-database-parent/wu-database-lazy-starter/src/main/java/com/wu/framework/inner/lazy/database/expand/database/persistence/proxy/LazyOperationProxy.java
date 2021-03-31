package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotationUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义接口实现方法执行代理类
 * @date : 2020/6/25 下午11:19
 */
@ConditionalOnBean(value = DataSource.class)
public class LazyOperationProxy implements InvocationHandler, InitializingBean {

    // method LazyOperationMethod
    private final static Map<String, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();
    private final DataSource dataSource;
    private final List<LazyOperationMethod> lazyOperationMethods;

    public LazyOperationProxy(DataSource dataSource, List<LazyOperationMethod> lazyOperationMethods) {
        this.dataSource = dataSource;
        this.lazyOperationMethods = lazyOperationMethods;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(methodName);
        if (null == lazyOperationMethod) throw new RuntimeException(String.format("无法处理方法【%s】", methodName));
        PersistenceRepository persistenceRepository = lazyOperationMethod.getPersistenceRepository(method, args);
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            return lazyOperationMethod.execute(preparedStatement, persistenceRepository);
        } catch (Exception exception) {
            throw exception;
        } finally {
            connection.close();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        lazyOperationMethods.stream().forEach(lazyOperationMethod -> {
            RepositoryOnDifferentMethods repositoryOnDifferentMethods = AnnotationUtils.getAnnotation(lazyOperationMethod.getClass(), RepositoryOnDifferentMethods.class);
            LAZY_OPERATION_METHOD_MAP.put(repositoryOnDifferentMethods.methodName(), lazyOperationMethod);
        });
    }
}
