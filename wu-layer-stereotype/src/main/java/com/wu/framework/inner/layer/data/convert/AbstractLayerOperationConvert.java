package com.wu.framework.inner.layer.data.convert;


import java.lang.reflect.Field;

/**
 * @author wujiawei
 */
public abstract class AbstractLayerOperationConvert<T> implements LayerOperationConvert<T> {

    /**
     * 处理对象赋值
     *
     * @param o     对象
     * @param field 对象中的字段
     * @param value 字段数据
     */
    @Override
    public void handler(Object o, Field field, Object value) throws IllegalAccessException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        Class fieldType = field.getType();
        T handler = handler(value, fieldType);
        field.set(o, handler);
    }
}
