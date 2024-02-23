package com.wu.framework.inner.layer.data.convert;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 数据工厂 转换
 *
 * @author Jia wei Wu
 */
public class LazyDataFactory {

    private static final List<LayerOperationConvert> layerOperationConvertList = Arrays.asList(
            new LayerOperationConvertDefault(),
            new LayerOperationConvertDate(),
            new LayerOperationConvertLocalDateTime(),
            new LayerOperationConvertEnums(),
            new LayerOperationConvertList(),
            new LayerOperationConvertArrayByte(),
            new LayerOperationConvertBean());


    /**
     * describe 对数据赋值
     *
     * @param o     对象
     * @param field 字段
     * @param value 字段value
     * @throws IllegalAccessException
     * @author Jia wei Wu
     * @date 2022/1/17 8:27 下午
     **/
    public static void handler(Object o, Field field, Object value) throws IllegalAccessException {
        for (LayerOperationConvert layerOperationConvert : layerOperationConvertList) {
            if (layerOperationConvert.support(field.getType())) {
                layerOperationConvert.handler(o, field, value);
                return;
            }
        }
    }

    /**
     * describe 将对象需要转成的类型
     *
     * @param o     原始对象
     * @param clazz 将对象需要转成的类型
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/17 8:27 下午
     **/
    public static <T> T handler(Object o, Class<T> clazz) {
        if (o == null) {
            return null;
        }
        for (LayerOperationConvert layerOperationConvert : layerOperationConvertList) {
            if (layerOperationConvert.support(clazz)) {
                return (T) layerOperationConvert.handler(o, clazz);
            }
        }
        return null;
    }

    /**
     * describe 将对象需要转成的类型
     *
     * @param o         原始对象
     * @param clazzType 将对象需要转成的类型
     * @return T
     * @author Jia wei Wu
     * @date 2022/1/17 8:27 下午
     **/
    public static <T> T handler(Object o, Type clazzType) {
        Assert.notNull(o, "原始对象不能为空");
        Class<?> actualTypeArgument = null;
        if (ParameterizedType.class.isAssignableFrom(clazzType.getClass())) {
            ParameterizedType parameterizedType = (ParameterizedType) clazzType;
            actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        } else if (Class.class.isAssignableFrom(clazzType.getClass())) {
            actualTypeArgument = (Class<?>) clazzType;
        }
        if (null == actualTypeArgument) {
            return null;
        }
        for (LayerOperationConvert layerOperationConvert : layerOperationConvertList) {
            if (layerOperationConvert.support(actualTypeArgument)) {
                return (T) layerOperationConvert.handler(o, actualTypeArgument);
            }
        }
        return null;
    }

    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    public static <T> boolean support(Type fieldType) {
        Class<?> actualTypeArgument = null;
        if (ParameterizedType.class.isAssignableFrom(fieldType.getClass())) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldType;
            actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        } else if (Class.class.isAssignableFrom(fieldType.getClass())) {
            actualTypeArgument = (Class<?>) fieldType;
        }
        if (null == actualTypeArgument) {
            return false;
        }
        for (LayerOperationConvert layerOperationConvert : layerOperationConvertList) {
            if (layerOperationConvert.support(actualTypeArgument)) {
                return true;
            }
        }
        return false;
    }
}
