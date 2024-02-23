package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Set;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 自动扫描包com.wu.framework.inner.database.expand.database.persistence.method 下面的class  不使用手动注册bean
 * @date : 2020/7/4 下午3:40
 */
@Deprecated
public class LazyOperationMethodFactory implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 手动注册bean
        Set<Class<?>> beanClazzs;
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        // 指定扫描的包名
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("com.wu.framework.inner.lazy.database.expand.database.persistence.method");
        for (BeanDefinition beanDefinition : candidateComponents) {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition;
            try {
                String beanName = beanDefinition.getBeanClassName();
                Class clazz = Class.forName(beanDefinition.getBeanClassName());
                if (clazz.isInterface()) {
                    continue;
                }
                RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(clazz);
                registry.registerBeanDefinition(beanName, rootBeanDefinition);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}