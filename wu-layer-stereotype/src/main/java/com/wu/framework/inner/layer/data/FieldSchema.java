package com.wu.framework.inner.layer.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字段架构
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/7/13 9:26 下午
 */
public interface FieldSchema {

    /**
     * 获取字段上的注解
     *
     * @param annotationType
     * @param <T>
     * @return
     */
    <T extends Annotation> T fieldAnnotation(Class<T> annotationType);

    /**
     * 存放当前字段
     *
     * @return
     */
    Field field();
}
