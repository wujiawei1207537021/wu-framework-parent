package com.wu.framework.inner.database.custom.database.persistence.factory;

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
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 自动扫描包com.wu.framework.inner.database.custom.database.persistence.mehtod 下面的class
 * @date : 2020/7/4 下午3:40
 */
public class LayerOperationMethodFactory implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 手动注册bean
        Set<Class<?>> beanClazzs;
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        // 指定扫描的包名
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents("com.wu.framework.inner.database.custom.database.persistence.mehtod");
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