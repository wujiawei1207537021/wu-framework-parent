package com.wu.framework.inner.database.custom.database.persistence.config;


import com.wu.framework.inner.database.custom.database.persistence.LayerOperation;
import com.wu.framework.inner.database.custom.database.persistence.factory.LayerOperationFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;


public class LayerOperationProxyBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LayerOperation.class);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(LayerOperation.class);
        definition.setBeanClass(LayerOperationFactory.class);
        //这里采用的是byType方式注入，类似的还有byName等
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        registry.registerBeanDefinition(LayerOperation.class.getSimpleName(), definition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {


    }
}

