package com.wu.framework.inner.database.expand.database.persistence.proxy;

import com.wu.framework.inner.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.database.expand.database.persistence.method.LayerOperationMethod;
import com.wu.framework.inner.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自定义接口实现方法执行代理类
 * @date : 2020/6/25 下午11:19
 */
public class LayerOperationProxy implements InvocationHandler, InitializingBean {

    private final Connection connection;
    private final List<LayerOperationMethod> layerOperationMethods;

    public LayerOperationProxy(DataSource dataSource, List<LayerOperationMethod> layerOperationMethods) throws SQLException {
        this.connection = dataSource.getConnection();
        this.layerOperationMethods = layerOperationMethods;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PersistenceRepository persistenceRepository;
        for (LayerOperationMethod layerOperationMethod : layerOperationMethods) {
            RepositoryOnDifferentMethods repositoryOnDifferentMethods = AnnotationUtils.getAnnotation(layerOperationMethod.getClass(), RepositoryOnDifferentMethods.class);
            if (method.getName().equals(repositoryOnDifferentMethods.value().getMethodName())) {
                persistenceRepository = layerOperationMethod.getPersistenceRepository(method, args);
                PreparedStatement pstm = connection.prepareStatement(persistenceRepository.getQueryString());
                return layerOperationMethod.execute(pstm, persistenceRepository.getResultType());
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
