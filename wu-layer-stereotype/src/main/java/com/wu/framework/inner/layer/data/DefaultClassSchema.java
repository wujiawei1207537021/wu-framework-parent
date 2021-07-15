package com.wu.framework.inner.layer.data;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/7/13 9:29 下午
 */
public class DefaultClassSchema implements ClassSchema {
    private final Class clazz;

    public DefaultClassSchema(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public <T extends Annotation> T classAnnotation(Class<T> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(clazz, annotationType);
    }

    @Override
    public Class clazz() {
        return clazz;
    }


    @Override
    public List<FieldSchema> fieldSchema() {
        return Arrays.stream(clazz.getDeclaredFields()).map(DefaultFieldSchema::new).collect(Collectors.toList());
    }
}
