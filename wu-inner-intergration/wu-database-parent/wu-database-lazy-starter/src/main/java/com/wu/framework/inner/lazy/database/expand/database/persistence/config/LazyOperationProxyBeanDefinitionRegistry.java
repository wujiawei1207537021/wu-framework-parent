package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.sql.DataSource;

@ConditionalOnBean(value = DataSource.class)
public class LazyOperationProxyBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LazyOperation.class);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(LazyOperation.class);
        definition.setBeanClass(LazyOperationFactory.class);
        //这里采用的是byType方式注入，类似的还有byName等
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        registry.registerBeanDefinition(LazyOperation.class.getSimpleName(), definition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

