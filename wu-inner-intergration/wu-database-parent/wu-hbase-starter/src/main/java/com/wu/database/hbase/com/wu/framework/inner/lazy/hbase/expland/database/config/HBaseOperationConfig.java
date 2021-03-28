package com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.config;

import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.persistence.HBaseOperation;
import com.wu.database.hbase.com.wu.framework.inner.lazy.hbase.expland.database.proxy.HBaseOperationProxy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 配置代理
 * @date : 2021/3/27 10:45 下午
 */
@Order(Integer.MIN_VALUE)
public class HBaseOperationConfig implements InitializingBean {


    private final HBaseOperationProxy hBaseOperationProxy;

    private final DefaultListableBeanFactory defaultListableBeanFactory;
    private final ConfigurableListableBeanFactory configurableListableBeanFactory;

    public HBaseOperationConfig(HBaseOperationProxy hBaseOperationProxy, DefaultListableBeanFactory defaultListableBeanFactory, ConfigurableListableBeanFactory configurableListableBeanFactory) {
        this.hBaseOperationProxy = hBaseOperationProxy;
        this.defaultListableBeanFactory = defaultListableBeanFactory;
        this.configurableListableBeanFactory = configurableListableBeanFactory;
    }




//    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(LazyOperationProxyBeanDefinitionRegistryAdapter.class);
//        registry.registerBeanDefinition("hBaseLazyOperationProxyBeanDefinitionRegistryAdapter", rootBeanDefinition);

        registry.registerBeanDefinition(HBaseOperation.class.getSimpleName(),
                (BeanDefinition) Proxy.newProxyInstance(HBaseOperation.class.getClassLoader(), new Class[]{HBaseOperation.class}, hBaseOperationProxy));

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        configurableListableBeanFactory.registerSingleton(HBaseOperation.class.getSimpleName(),
              Proxy.newProxyInstance(HBaseOperation.class.getClassLoader(), new Class[]{HBaseOperation.class}, hBaseOperationProxy));

    }
}
