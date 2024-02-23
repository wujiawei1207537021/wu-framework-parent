package com.wu.framework.inner.lazy.persistence.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * describe : 扫描class
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 6:40 下午
 */
public final class ScanClassUtil {
    /**
     * describe 扫描 指定路径下的class
     *
     * @param packagePath 包路径
     * @param annotation  指定注解
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 6:41 下午
     **/
    public static List<Class> scanClass(String packagePath, Class<? extends Annotation> annotation) {
        List<Class> classList = new ArrayList<>();
        if (ObjectUtils.isEmpty(packagePath)) {
            return classList;
        }
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> true;
        provider.addIncludeFilter(includeFilter);
        Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
        // 指定扫描的包名
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(packagePath);
        beanDefinitionSet.addAll(candidateComponents);

        beanDefinitionSet.forEach(beanDefinition -> {
            try {
                Class clazz = Class.forName(beanDefinition.getBeanClassName());

                if (!ObjectUtils.isEmpty(annotation)) {
                    if (!ObjectUtils.isEmpty(AnnotationUtils.getAnnotation(clazz, annotation))) {
                        classList.add(clazz);
                    }
                } else {
                    classList.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classList;
    }
}
