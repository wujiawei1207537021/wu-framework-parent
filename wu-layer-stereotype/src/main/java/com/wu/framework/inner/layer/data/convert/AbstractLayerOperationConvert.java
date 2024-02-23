package com.wu.framework.inner.layer.data.convert;


import java.lang.reflect.Field;

/**
 * @author Jia wei Wu
 */
public abstract class AbstractLayerOperationConvert  implements LayerOperationConvert {

    /**
     * 处理对象赋值
     *
     * @param o     对象
     * @param field 对象中的字段
     * @param value 字段数据
     */
    @Override
    public <T> void handler(Object o, Field field, Object value) throws IllegalAccessException {
        if (null == value || null == o) {
            return;
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        T handler = handler(value, field);
        try {
            field.set(o, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
