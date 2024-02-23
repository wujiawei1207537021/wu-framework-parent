package com.wu.framework.easy.mysql.binlog.listener.config;

import java.lang.reflect.Method;
import java.util.List;

public class MultiMethodMySQLBinlogBinlogListenerEndpoint<K, V> extends MethodMySQLBinlogBinlogListenerEndpoint {

    private final List<Method> methods;
    private final Method defaultMethod;

    public MultiMethodMySQLBinlogBinlogListenerEndpoint(List<Method> methods, Object bean) {
        this(methods, null, bean);

    }


    public MultiMethodMySQLBinlogBinlogListenerEndpoint(List<Method> methods, Method defaultMethod, Object bean) {
        this.methods = methods;
        this.defaultMethod = defaultMethod;
        setBean(bean);
    }
}
