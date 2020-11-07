package com.wu.framework.inner.database.custom.database.persistence.proxy;

import com.wu.framework.inner.database.CustomDataSourceAdapter;
import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.custom.database.persistence.mehtod.LayerOperationMethod;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

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

    public LayerOperationProxy(CustomDataSourceAdapter customDataSourceAdapter, List<LayerOperationMethod> layerOperationMethods) throws SQLException {
        this.connection = customDataSourceAdapter.getCustomDataSource().getConnection();
        this.layerOperationMethods = layerOperationMethods;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CustomPersistenceRepository customPersistenceRepository = null;
        for (LayerOperationMethod layerOperationMethod : layerOperationMethods) {
            GetCustomRepositoryOnDifferentMethods getCustomRepositoryOnDifferentMethods = AnnotationUtils.getAnnotation(layerOperationMethod.getClass(), GetCustomRepositoryOnDifferentMethods.class);
            if (method.getName().equals(getCustomRepositoryOnDifferentMethods.value().getMethodName())) {
                customPersistenceRepository = layerOperationMethod.getCustomRepository(method, args);
                PreparedStatement pstm = connection.prepareStatement(customPersistenceRepository.getQueryString());
                return layerOperationMethod.execute(pstm, customPersistenceRepository.getResultType());
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
