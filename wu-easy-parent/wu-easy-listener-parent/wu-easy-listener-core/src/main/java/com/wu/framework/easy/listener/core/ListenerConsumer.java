package com.wu.framework.easy.listener.core;


import org.springframework.scheduling.SchedulingAwareRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public interface ListenerConsumer extends SchedulingAwareRunnable {


//    /**
//     * 获取当前数据
//     *
//     * @param consumer
//     */
//    void accept(Consumer consumer);

    // 数据 序列化


    // 处理 反射需要的数据
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


}
