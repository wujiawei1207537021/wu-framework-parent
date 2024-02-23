package com.wu.framework.inner.layer.data.convert;

import java.lang.reflect.Field;

/**
 * 数据转换
 *
 * @author wujiawei
 */
public interface LayerOperationConvert<T> extends LayerConvert<T> {

    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    boolean support(Class<T> fieldType);

    /**
     * 处理对象赋值
     *
     * @param o     对象
     * @param field 对象中的字段
     * @param value 字段数据
     */
    void handler(Object o, Field field, Object value) throws IllegalAccessException;

    /**
     * 将对象转换成指定类型的对象
     *
     * @param value     对象
     * @param fieldType 对象转换后的类型
     * @param <T>
     * @return
     */
    T handler(Object value, Class fieldType);


}
