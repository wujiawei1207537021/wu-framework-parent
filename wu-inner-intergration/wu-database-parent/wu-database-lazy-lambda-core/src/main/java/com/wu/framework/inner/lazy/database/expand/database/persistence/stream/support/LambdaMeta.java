package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support;

public interface LambdaMeta {

    /**
     * 获取 lambda 表达式实现方法的名称
     *
     * @return lambda 表达式对应的实现方法名称
     */
    String methodName();

    /**
     * 实例化该方法的类
     *
     * @return 返回对应的类名称
     */
    Class<?> instantiatedClass();

}
