package com.wu.framework.easy.pulsar.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodPulsarListenerEndpoint<K, V> extends MethodPulsarListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodPulsarListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodPulsarListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
