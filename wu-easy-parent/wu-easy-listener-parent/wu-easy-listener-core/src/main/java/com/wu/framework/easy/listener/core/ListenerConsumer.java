package com.wu.framework.easy.listener.core;


import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecordType;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.springframework.scheduling.SchedulingAwareRunnable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public interface ListenerConsumer extends SchedulingAwareRunnable {


    /**
     * description 处理 反射需要的数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 6:13 下午
     */
    default Object[] invokeArgs(Class<?>[] parameterTypes, Object... source) {

        Object[] args = new Object[parameterTypes.length];

        final List<Object> parameter = Arrays.stream(source).filter(Objects::nonNull).collect(Collectors.toList());

        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterType = parameterTypes[i];

            for (Object o : parameter) {
                //优先完全匹配
                if (o.getClass().equals(parameterType)) {
                    args[i] = o;
                    break;
                }
                // 判断 source 中是否含有 parameterType 的子类
                if (parameterType.isAssignableFrom(o.getClass())) {
                    args[i] = o;
                }
            }
        }
        return args;
    }

    /**
     * description 是否包含指定类型参数
     *
     * @param parameterTypes 参数类型
     * @param clazz          指定类型
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 6:15 下午
     */
    default <T> Class<T> includeParameterTypes(Class<?>[] parameterTypes, Class<T> clazz) {
        if (ObjectUtils.isEmpty(clazz)) {
            return null;
        }
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType.isAssignableFrom(clazz)) {
                return (Class<T>) parameterType;
            }
        }
        return null;
    }

    /**
     * description 获取 consumerRecord 中的范型
     *
     * @param method 方法
     * @return 返回数据类型
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/16 11:06 上午
     */
    default ConsumerRecordType consumerRecord(Method method) {
        // 获取该方法的参数类型信息（带有泛型）
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        // 但我们实际上需要获取返回值类型中的泛型信息，所以要进一步判断，即判断获取的返回值类型是否是参数化类型ParameterizedType
        ConsumerRecordType consumerRecordType = new ConsumerRecordType();
        for (Type genericParameterType : genericParameterTypes) {
            if (ParameterizedType.class.isAssignableFrom(genericParameterType.getClass())) {
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
                if (parameterizedType.getRawType().equals(ConsumerRecord.class)) {
                    // 获取成员方法参数的泛型类型信息
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    consumerRecordType.setSchemaType((Class) actualTypeArguments[0]);
                    consumerRecordType.setPayloadType((Class) actualTypeArguments[1]);
                    return consumerRecordType;
                }
            }
        }

        consumerRecordType.setPayloadType(Object.class);
        consumerRecordType.setSchemaType(Object.class);
        return consumerRecordType;
    }


    /**
     * 序列化数据
     *
     * @param rowValue    行数据
     * @param payloadType 负载对应的class
     * @return
     */
    default Object serializedPayload(Map<String, Object> rowValue, Class<?> payloadType) {
        // 序列化数据
        Object payload = null;
        if (EasyHashMap.class.isAssignableFrom(payloadType)) {
            EasyHashMap<Object, Object> easyHashMap = new EasyHashMap<>();
            easyHashMap.putAll(rowValue);
            return easyHashMap;
        }
        if (Map.class.isAssignableFrom(payloadType)) {
            return rowValue;
        }
        if (Object.class.equals(payloadType)) {
            return rowValue;
        }
        try {
            payload = payloadType.newInstance();
            for (Field declaredField : payloadType.getDeclaredFields()) {
                declaredField.setAccessible(true);
                // 驼峰
                String name = declaredField.getName();
                // 下划线
                String humpToLineName = CamelAndUnderLineConverter.humpToLine2(name);
                Class declaredFieldType = declaredField.getType();
                if (rowValue.containsKey(humpToLineName)) {
                    Object value = rowValue.get(humpToLineName);
                    if (null != value) {
                        if (Boolean.class.isAssignableFrom(declaredFieldType)) {
                            if (String.class.isAssignableFrom(value.getClass())) {
                                value = new Boolean(value.toString());
                            } else if (Integer.class.isAssignableFrom(value.getClass())) {
                                value = (Integer) value != 0;
                            }
                        } else if (Enum.class.isAssignableFrom(declaredFieldType)) {
                            value = Enum.valueOf(declaredFieldType, value.toString());
                        }
                    }
                    declaredField.set(payload, value);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return payload;
    }

}
