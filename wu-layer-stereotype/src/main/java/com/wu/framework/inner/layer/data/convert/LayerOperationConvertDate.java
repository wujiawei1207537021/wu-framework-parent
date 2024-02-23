package com.wu.framework.inner.layer.data.convert;


import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * describe: 数组转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:15
 */
public class LayerOperationConvertDate extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        return fieldType.equals(java.util.Date.class) || fieldType.equals(java.sql.Date.class);
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue 对象
     * @param fieldType  对象转换后的类型
     * @return
     */
    @Override
    public Object handler(Object fieldValue, Class fieldType) {
        Class<?> fieldValueClass = fieldValue.getClass();
        if (java.util.Date.class.isAssignableFrom(fieldValueClass)) {
            return fieldValue;
        } else if (java.sql.Date.class.isAssignableFrom(fieldValueClass)) {
            java.sql.Date timestamp = (java.sql.Date) fieldValue;
            return new java.util.Date(timestamp.getTime());
        } else if (LocalDateTime.class.isAssignableFrom(fieldValueClass)) {
            LocalDateTime timestamp = (LocalDateTime) fieldValue;
            return new java.util.Date(timestamp.getNano());
        }
        return null;
    }

    /**
     * 将对象转换成指定类型的字段对象
     *
     * @param fieldValue 对象
     * @param field      字段
     * @return
     */
    @Override
    public Object handler(Object fieldValue, Field field) {
        return handler(fieldValue, field.getType());
    }
}
