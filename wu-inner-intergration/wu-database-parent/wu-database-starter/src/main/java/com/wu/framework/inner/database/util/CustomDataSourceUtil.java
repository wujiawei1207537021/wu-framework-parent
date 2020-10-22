package com.wu.framework.inner.database.util;

import com.wu.framework.inner.database.config.SimpleCustomDatabaseConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 创建数据库链接
 * @date : 2020/6/25 下午11:13
 */
public class CustomDataSourceUtil {

    /**
     * 用于获取一个连接
     *
     * @param simpleCustomDatabaseConfiguration
     * @return
     */
    public static Connection getConnection(SimpleCustomDatabaseConfiguration simpleCustomDatabaseConfiguration) {
        try {
            Class.forName(simpleCustomDatabaseConfiguration.getDriver().getName());
            return DriverManager.getConnection(simpleCustomDatabaseConfiguration.getUrl(), simpleCustomDatabaseConfiguration.getUsername(), simpleCustomDatabaseConfiguration.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Set<Class> scanClass(List<String> scanBeanPath, Class<? extends Annotation> annotationType) {
        if (ObjectUtils.isEmpty(scanBeanPath)) {
            return null;
        }
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
        for (String s : scanBeanPath) {
            // 指定扫描的包名
            Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(s);
            beanDefinitionSet.addAll(candidateComponents);
        }

        Set<Class> classSet = new HashSet<>();
        beanDefinitionSet.forEach(beanDefinition -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition;
            try {
                if (null != annotationType && null == AnnotationUtils.getAnnotation(definition.getBeanClass(), annotationType)) {
                    return;
                }
                classSet.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(definition.getBeanClassName());
        });
        return classSet;

    }

}