package com.wu.framework.easy.listener.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodKafkaListenerEndpoint<K, V> extends MethodKafkaListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodKafkaListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodKafkaListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
