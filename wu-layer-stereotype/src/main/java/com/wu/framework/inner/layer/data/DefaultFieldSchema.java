package com.wu.framework.inner.layer.data;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/7/13 9:34 下午
 */
public class DefaultFieldSchema implements FieldSchema {
    private final Field field;

    public DefaultFieldSchema(Field field) {
        this.field = field;
    }

    @Override
    public <T extends Annotation> T fieldAnnotation(Class<T> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(field, annotationType);
    }

}
