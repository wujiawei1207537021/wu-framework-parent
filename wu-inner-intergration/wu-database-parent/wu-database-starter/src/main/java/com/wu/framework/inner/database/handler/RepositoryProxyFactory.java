package com.wu.framework.inner.database.handler;

import com.wu.framework.inner.database.proxy.RepositoryProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/6/27 下午7:20
 */


public class RepositoryProxyFactory<T> implements FactoryBean<T> {


    private final Class<T> interfaceType;
    private final RepositoryProxy repositoryProxy;


    public RepositoryProxyFactory(Class<T> interfaceType, RepositoryProxy repositoryProxy) {

        this.interfaceType = interfaceType;
        this.repositoryProxy = repositoryProxy;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, repositoryProxy);
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