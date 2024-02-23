package com.wu.framework.easy.redis.listener.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodRedisListenerEndpoint<K, V> extends MethodRedisListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodRedisListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodRedisListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
