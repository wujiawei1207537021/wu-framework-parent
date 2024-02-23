package com.wu.framework.inner.layer.data.convert;


/**
 * @author wujiawei
 */
public class LayerOperationConvertDefault extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param valueClass 字段类型
     * @return
     */
    @Override
    public boolean support(Class valueClass) {
        String packageName = valueClass.getPackage().getName();
        if (valueClass.equals(java.lang.Integer.class) ||
                valueClass.equals(java.lang.Byte.class) ||
                valueClass.equals(java.lang.Long.class) ||
                valueClass.equals(java.lang.Double.class) ||
                valueClass.equals(java.lang.Float.class) ||
                valueClass.equals(java.lang.Character.class) ||
                valueClass.equals(java.lang.Short.class) ||
                valueClass.equals(java.lang.Boolean.class) ||
                packageName.startsWith("java")) {
            return true;
        }
        return false;
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param value 对象
     * @param clazz 对象转换后的类型
     * @return
     */
    @Override
    public Object handler(Object value, Class clazz) {
        if (null == value) {
            return null;
        }
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (clazz.equals(String.class)) {
            return value.toString();
        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            return Short.valueOf(value.toString());
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            return Float.valueOf(value.toString());
        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return value;
        } else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            return value;
        }
        return value;
    }
}
