package com.wu.framework.inner.layer.data.convert;


import com.wu.framework.inner.layer.util.JsonUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * describe: 数组转换
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 20:15
 */
public class LayerOperationConvertBean extends AbstractLayerOperationConvert {


    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    @Override
    public boolean support(Class<?> fieldType) {
        return true;
    }

    /**
     * 将对象转换成指定类型的对象
     *
     * @param fieldValue 对象
     * @param fieldType  对象转换后的类型
     * @return
     */
    @Override
    public Object handler(Object fieldValue, Class fieldType) {
        if (fieldValue != null && Map.class.isAssignableFrom(fieldValue.getClass())) {
            try {
                // map 转换
                Object instance = fieldType.getDeclaredConstructor().newInstance();
                // 获取字段
                for (Field declaredField : fieldType.getDeclaredFields()) {
                    String name = declaredField.getName();
                    if (((Map<?, ?>) fieldValue).containsKey(name)) {
                        Object o = ((Map<?, ?>) fieldValue).get(name);
                        Class<?> declaredFieldFieldType = declaredField.getType();
                        Object instanceFieldValue = LazyDataFactory.handler(o, declaredFieldFieldType);
                        declaredField.setAccessible(true);
                        declaredField.set(instance, instanceFieldValue);
                    }
                }
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(fieldValue!=null&&fieldValue.getClass().equals(String.class)){
            return JsonUtils.parseObject(fieldValue.toString(), fieldType);
        }else {
            return JsonUtils.parseObject(JsonUtils.toJsonString(fieldValue), fieldType);
        }
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
