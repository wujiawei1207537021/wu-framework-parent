package com.wu.framework.inner.layer.data.convert;


import java.lang.reflect.Field;
import java.math.BigInteger;

/**
 * describe 默认数据转换
 *
 * @author Jia wei Wu
 * @date 2022/1/30 20:15
 **/
public class LayerOperationConvertDefault extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        try {
            String packageName = fieldType.getPackage().getName();
            if (fieldType.equals(java.lang.Integer.class) ||
                    fieldType.equals(java.lang.Byte.class) ||
                    fieldType.equals(java.lang.Long.class) ||
                    fieldType.equals(java.lang.Double.class) ||
                    fieldType.equals(java.lang.Float.class) ||
                    fieldType.equals(java.lang.Character.class) ||
                    fieldType.equals(java.lang.Short.class) ||
                    fieldType.equals(java.lang.Boolean.class) || fieldType.equals(String.class)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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
        if (null == fieldValue) {
            return null;
        }
        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.valueOf(fieldValue.toString());
        } else if (fieldType.equals(String.class)) {
            return fieldValue.toString();
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.valueOf(fieldValue.toString());
        } else if (fieldType.equals(BigInteger.class)) {
            return new BigInteger(fieldValue.toString());
        } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
            return Short.valueOf(fieldValue.toString());
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.valueOf(fieldValue.toString());
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            return Float.valueOf(fieldValue.toString());
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.valueOf(fieldValue.toString());
        } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
            return fieldValue;
        } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
            return fieldValue;
        }
        return fieldValue;
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
        Class<?> fieldType = field.getType();
        try {

            if (null == fieldValue) {
                return null;
            }
            Class<?> fieldValueClass = fieldValue.getClass();
            if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                if (fieldValueClass.equals(Boolean.class) || fieldValueClass.equals(boolean.class)) {
                    return fieldValue.equals(true) ? 1 : 0;
                }
                return Integer.valueOf(fieldValue.toString());
            } else if (fieldType.equals(String.class)) {
                return fieldValue.toString();
            } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                return Long.valueOf(fieldValue.toString());
            } else if (fieldType.equals(BigInteger.class)) {
                return new BigInteger(fieldValue.toString());
            } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
                return Short.valueOf(fieldValue.toString());
            } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                return Double.valueOf(fieldValue.toString());
            } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                return Float.valueOf(fieldValue.toString());
            } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                return Boolean.valueOf(fieldValue.toString());
            } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
                return fieldValue;
            } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
                return fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法将数据" + fieldValue + "转换为" + field.getDeclaringClass() + "." + field.getName() + "中属性" + fieldType);
        }
        return fieldValue;
    }
}
