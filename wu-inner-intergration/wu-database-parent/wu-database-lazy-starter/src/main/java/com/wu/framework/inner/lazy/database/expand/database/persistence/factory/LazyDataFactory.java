package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.layer.data.convert.LayerOperationConvert;
import com.wu.framework.inner.layer.data.convert.LayerOperationConvertDefault;
import com.wu.framework.inner.layer.data.convert.LayerOperationConvertEnums;
import com.wu.framework.inner.layer.data.convert.LayerOperationConvertList;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 数据工厂 转换
 */
public class LazyDataFactory {

    private static List<LayerOperationConvert> layerOperationConvertList = Arrays.asList(new LayerOperationConvertDefault(), new LayerOperationConvertEnums(), new LayerOperationConvertList());


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


}
