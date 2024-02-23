package com.wu.framework.inner.layer.data.convert;

import java.lang.reflect.Field;

/**
 * 数据转换
 *
 * @author Jia wei Wu
 */
public interface LayerOperationConvert extends LayerConvert {

    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return boolean
     */
    @Override
    boolean support(Class<?> fieldType);

    /**
     * 处理对象赋值
     *
     * @param o     对象
     * @param field 对象中的字段
     * @param value 字段数据
     */
   <T> void handler(Object o, Field field, Object value) throws IllegalAccessException;

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue 对象
     * @param fieldType  对象转换后的类型
     * @return T
     */
    <T> T handler(Object fieldValue, Class<?> fieldType);

    /**
     * 将对象转换成指定类型的字段对象
     *
     * @param fieldValue 对象
     * @param field      字段
     * @param <T>
     * @return
     */
    <T> T handler(Object fieldValue, Field field);


}
