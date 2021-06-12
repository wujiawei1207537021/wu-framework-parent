package com.wu.framework.authorization.function;

import java.util.Collection;

/**
 * ShiroFunction
 */
@FunctionalInterface
public interface AuthorizationFunction<T, A> {

    /**
     * 通过注解获取对象
     * T 接口 A 注解类型
     *
     * @return
     */
    T getBeanWithAnnotation(Collection<T> t, A a);

}
