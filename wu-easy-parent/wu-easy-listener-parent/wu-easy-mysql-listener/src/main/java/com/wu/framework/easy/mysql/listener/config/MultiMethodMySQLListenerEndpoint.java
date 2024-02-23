package com.wu.framework.easy.mysql.listener.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodMySQLListenerEndpoint<K, V> extends MethodMySQLListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodMySQLListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodMySQLListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
