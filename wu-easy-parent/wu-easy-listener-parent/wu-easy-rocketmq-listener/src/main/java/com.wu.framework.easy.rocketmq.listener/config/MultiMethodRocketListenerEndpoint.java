package com.wu.framework.easy.rocketmq.listener.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodRocketListenerEndpoint<K, V> extends MethodRocketListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodRocketListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodRocketListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
