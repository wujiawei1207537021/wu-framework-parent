package com.wu.framework.inner.layer.data.convert;


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
    public boolean support(Class fieldType) {
        try {
            String packageName = fieldType.getPackage().getName();
            if (fieldType.equals(java.lang.Integer.class) ||
                    fieldType.equals(java.lang.Byte.class) ||
                    fieldType.equals(java.lang.Long.class) ||
                    fieldType.equals(java.lang.Double.class) ||
                    fieldType.equals(java.lang.Float.class) ||
                    fieldType.equals(java.lang.Character.class) ||
                    fieldType.equals(java.lang.Short.class) ||
                    fieldType.equals(java.lang.Boolean.class) ||
                    packageName.startsWith("java")) {
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
     * @param value     对象
     * @param fieldType 对象转换后的类型
     * @return
     */
    @Override
    public Object handler(Object value, Class fieldType) {
        if (null == value) {
            return null;
        }
        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return Integer.valueOf(value.toString());
        } else if (fieldType.equals(String.class)) {
            return value.toString();
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return Long.valueOf(value.toString());
        } else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
            return Short.valueOf(value.toString());
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return Double.valueOf(value.toString());
        } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
            return Float.valueOf(value.toString());
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return Boolean.valueOf(value.toString());
        } else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
            return value;
        } else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
            return value;
        }
        return value;
    }
}
