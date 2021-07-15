package com.wu.framework.inner.layer.data;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @param
 * @author Jia wei Wu
 * @describe 类架构
 * @return
 * @date 2021/7/13 9:16 下午
 **/
public interface ClassSchema {


    <T extends Annotation> T classAnnotation(Class<T> annotationType);

    Class clazz();

    List<FieldSchema> fieldSchema();


}
