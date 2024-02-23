package com.wu.framework.inner.layer.data.convert;


/**
 * describe: 数组转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:15
 */
public class LayerOperationConvertList extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class fieldType) {
        return fieldType.isArray();
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
        return null;
    }

}
