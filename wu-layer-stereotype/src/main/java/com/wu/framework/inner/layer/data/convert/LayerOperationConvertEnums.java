package com.wu.framework.inner.layer.data.convert;


public class LayerOperationConvertEnums extends AbstractLayerOperationConvert<Enum> {


    /**
     * 支持
     *
     * @param valueClass 字段类型
     * @return
     */
    @Override
    public boolean support(Class<Enum> valueClass) {
        return valueClass.isEnum();
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param value 对象
     * @param clazz 对象转换后的类型
     * @return
     */
    @Override
    public Enum handler(Object value, Class clazz) {
        final Enum anEnum = Enum.valueOf(clazz, value.toString());
        return anEnum;
    }


}
