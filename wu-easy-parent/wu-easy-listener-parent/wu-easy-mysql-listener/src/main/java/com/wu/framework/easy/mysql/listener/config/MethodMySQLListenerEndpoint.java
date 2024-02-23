package com.wu.framework.easy.mysql.listener.config;

import java.lang.reflect.Method;

public class MethodMySQLListenerEndpoint extends AbstractMySQLListenerEndpoint {
    private Object bean;

    private Method method;

    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
