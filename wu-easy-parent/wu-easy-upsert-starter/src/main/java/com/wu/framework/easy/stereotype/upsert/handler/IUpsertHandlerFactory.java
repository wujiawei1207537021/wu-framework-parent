package com.wu.framework.easy.stereotype.upsert.handler;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/7/14 下午8:08
 */
public class IUpsertHandlerFactory<T> implements FactoryBean<T> {


    private final Class<T> interfaceType;
    private final IUpsertHandler iUpsertHandler;


    public IUpsertHandlerFactory(Class<T> interfaceType, IUpsertHandler iUpsertHandler) {

        this.interfaceType = interfaceType;
        this.iUpsertHandler = iUpsertHandler;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, iUpsertHandler);
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
