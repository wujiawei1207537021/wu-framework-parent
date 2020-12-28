package com.wu.framework.inner.lazy.database.handler;

import com.wu.framework.inner.lazy.database.proxy.RepositoryProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/6/27 下午7:20
 */

public class RepositoryProxyFactory implements FactoryBean {

    private final Class interfaceType;

    private final RepositoryProxy repositoryProxy;

    public RepositoryProxyFactory(Class interfaceType, RepositoryProxy repositoryProxy) {
        this.interfaceType = interfaceType;
        this.repositoryProxy = repositoryProxy;
    }

    @Override
    public Object getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        return Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, repositoryProxy);
    }

    @Override
    public Class getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}