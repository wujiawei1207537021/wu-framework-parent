package com.wu.framework.inner.layer.data.convert;

/**
 * describe: 枚举转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:16
 */
public class LayerOperationConvertEnums extends AbstractLayerOperationConvert<Enum> {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<Enum> fieldType) {
        return fieldType.isEnum();
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param value     对象
     * @param fieldType 对象转换后的类型
     * @return
     */
    @Override
    public Enum handler(Object value, Class fieldType) {
        final Enum anEnum = Enum.valueOf(fieldType, value.toString());
        return anEnum;
    }


}
