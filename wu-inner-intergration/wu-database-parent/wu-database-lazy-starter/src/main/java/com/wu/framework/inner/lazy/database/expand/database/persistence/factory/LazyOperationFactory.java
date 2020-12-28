package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/6/27 下午7:20
 */


public class LazyOperationFactory<T> implements FactoryBean<T> {

    private final Class<T> interfaceType;
    private final LazyOperationProxy lazyOperationProxy;


    public LazyOperationFactory(Class<T> interfaceType, LazyOperationProxy lazyOperationProxy) {
        this.interfaceType = interfaceType;
        this.lazyOperationProxy = lazyOperationProxy;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, lazyOperationProxy);
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