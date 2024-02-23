package com.wu.framework.inner.layer.data.convert;

import java.lang.reflect.Field;

/**
 * describe: 枚举转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:16
 */
public class LayerOperationConvertEnums extends AbstractLayerOperationConvert{


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        return fieldType.isEnum();
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue 对象
     * @param fieldType  对象转换后的类型
     * @return
     */
    @Override
    public Enum handler(Object fieldValue, Class fieldType) {
        final Enum anEnum = Enum.valueOf(fieldType, fieldValue.toString());
        return anEnum;
    }

    /**
     * 将对象转换成指定类型的字段对象
     *
     * @param fieldValue 对象
     * @param field      字段
     * @return
     */
    @Override
    public Enum handler(Object fieldValue, Field field) {
        Class fieldType = field.getType();
        try {
            Enum anEnum = Enum.valueOf(fieldType, fieldValue.toString());
            return anEnum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
