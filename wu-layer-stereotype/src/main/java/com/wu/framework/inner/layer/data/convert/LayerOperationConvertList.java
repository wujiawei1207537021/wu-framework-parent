package com.wu.framework.inner.layer.data.convert;


import com.fasterxml.jackson.core.type.TypeReference;
import com.wu.framework.inner.layer.util.JsonUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe: 数组转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:15
 */
public class LayerOperationConvertList extends AbstractLayerOperationConvert {
    private static final List<LayerOperationConvert> layerOperationConvertList = Arrays.asList(
            new LayerOperationConvertDefault(),
            new LayerOperationConvertDate(),
            new LayerOperationConvertLocalDateTime(),
            new LayerOperationConvertEnums(),
            new LayerOperationConvertList(),
            new LayerOperationConvertArrayByte(),
            new LayerOperationConvertBean());

    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        return Collection.class.isAssignableFrom(fieldType);
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue     对象
     * @param fieldNormative 字段List中的类型
     * @return
     */
    @Override
    public List handler(Object fieldValue, Class fieldNormative) {
        try {
            List<Object> list = JsonUtils.parseObject(fieldValue.toString(), new TypeReference<List>() {
            });
            List<Object> objectList = list.stream().map(item -> {
                for (LayerOperationConvert layerOperationConvert : layerOperationConvertList) {
                    if (layerOperationConvert.support(fieldNormative)) {
                        if (item != null && item.getClass().equals(String.class)) {
                            return layerOperationConvert.handler(item.toString(), fieldNormative);
                        } else {
                            return layerOperationConvert.handler(JsonUtils.toJsonString(item), fieldNormative);
                        }

                    }
                }
                return null;
            }).collect(Collectors.toList());
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
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
    public List handler(Object fieldValue, Field field) {
        if (ObjectUtils.isEmpty(fieldValue)) {
            return null;
        }
        Class<?> fieldNormative;
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            fieldNormative = (Class<?>) actualTypeArguments[0];
        } else {
            fieldNormative = field.getType();
        }

        return handler(fieldValue, fieldNormative);
    }
}
