package com.wu.framework.inner.lazy.database.expand.database.persistence.proxy;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义接口实现方法执行代理类
 * @date : 2020/6/25 下午11:19
 */
public class LazyOperationProxy implements InvocationHandler, InitializingBean {

    private final Connection connection;
    private final List<LazyOperationMethod> lazyOperationMethods;
    // method LazyOperationMethod
    private final static Map<String, LazyOperationMethod> LAZY_OPERATION_METHOD_MAP = new HashMap<>();

    public LazyOperationProxy(DataSource dataSource, List<LazyOperationMethod> lazyOperationMethods) throws SQLException {
        this.connection = dataSource.getConnection();
        this.lazyOperationMethods = lazyOperationMethods;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LazyOperationMethod lazyOperationMethod = LAZY_OPERATION_METHOD_MAP.get(method.getName());
        PersistenceRepository  persistenceRepository = lazyOperationMethod.getPersistenceRepository(method, args);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        return lazyOperationMethod.execute(preparedStatement, persistenceRepository.getResultType());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        lazyOperationMethods.stream().forEach(lazyOperationMethod -> {
            RepositoryOnDifferentMethods repositoryOnDifferentMethods = AnnotationUtils.getAnnotation(lazyOperationMethod.getClass(), RepositoryOnDifferentMethods.class);
            LAZY_OPERATION_METHOD_MAP.put(repositoryOnDifferentMethods.value().getMethodName(), lazyOperationMethod);
        });
    }
}
