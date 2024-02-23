package com.wu.framework.inner.layer.data.convert;


import com.wu.framework.inner.layer.util.BinHexSwitchUtil;

import java.lang.reflect.Field;
import java.sql.Blob;

/**
 * describe: 数组byte 转换 byte[]
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:15
 */
public class LayerOperationConvertArrayByte extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        return fieldType.equals(byte[].class) || fieldType.equals(Byte[].class);
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue 对象
     * @param fieldType  对象转换后的类型
     * @return byte[]
     */
    @Override
    public Object handler(Object fieldValue, Class fieldType) {
        Class<?> fieldValueClass = fieldValue.getClass();
        if (java.sql.Blob.class.isAssignableFrom(fieldValueClass)) {
            Blob blob= (Blob) fieldValue;
            return BinHexSwitchUtil.blobToBytes(blob);
        } else if (byte[].class.isAssignableFrom(fieldValueClass)||Byte[].class.isAssignableFrom(fieldValueClass)) {
            return fieldValue;
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
