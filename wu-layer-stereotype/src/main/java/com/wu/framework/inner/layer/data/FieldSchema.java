package com.wu.framework.inner.layer.data;

import java.lang.annotation.Annotation;

/**
 * @describe: 字段架构
 * @author : Jia wei Wu
 * @date : 2021/7/13 9:26 下午
 * @version : 1.0
 */
public interface FieldSchema {

    <T extends Annotation> T fieldAnnotation(Class<T> annotationType);
}
