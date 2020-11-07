package com.wu.framework.inner.database.custom.database.persistence.factory;

import com.wu.framework.inner.database.custom.database.persistence.proxy.LayerOperationProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/6/27 下午7:20
 */


public class LayerOperationFactory<T> implements FactoryBean<T> {

    private final Class<T> interfaceType;
    private final LayerOperationProxy layerOperationProxy;


    public LayerOperationFactory(Class<T> interfaceType, LayerOperationProxy layerOperationProxy) {
        this.interfaceType = interfaceType;
        this.layerOperationProxy = layerOperationProxy;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, layerOperationProxy);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}