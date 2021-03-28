package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 代理类自动注入适配器
 * @date : 2021/3/27 9:53 下午
 */
public class LazyOperationProxyBeanDefinitionRegistryAdapter implements BeanDefinitionRegistryPostProcessor {

    private final Class interfaceType;
    private final InvocationHandler invocationHandler;

    public LazyOperationProxyBeanDefinitionRegistryAdapter(Class interfaceType, InvocationHandler invocationHandler) {
        this.interfaceType = interfaceType;
        this.invocationHandler = invocationHandler;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(interfaceType);
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//        definition.getConstructorArgumentValues().addGenericArgumentValue(interfaceType);
//
//        definition.setBeanClass(LazyOperationFactory.class);
//
//        //这里采用的是byType方式注入，类似的还有byName等
//        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//
//        registry.registerBeanDefinition(interfaceType.getSimpleName(), definition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerSingleton(interfaceType.getSimpleName(), Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, invocationHandler));
    }


}
