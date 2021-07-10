//package com.wu.framework.easy.stereotype.upsert.handler;
//
//import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//
//
//@Deprecated
//public class IUpsertProxyBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(IUpsert.class);
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//        definition.getConstructorArgumentValues().addGenericArgumentValue(IUpsert.class);
//        definition.setBeanClass(IUpsertHandlerFactory.class);
//        //这里采用的是byType方式注入，类似的还有byName等
//        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//        registry.registerBeanDefinition(IUpsert.class.getSimpleName(), definition);
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//
//
//    }
//}
//
