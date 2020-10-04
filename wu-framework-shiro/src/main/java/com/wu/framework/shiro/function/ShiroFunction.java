package com.wu.framework.shiro.function;

import java.util.Collection;

/**
 *
 *ShiroFunction
 *
 */
@FunctionalInterface
public interface ShiroFunction<T,A> {

    /**通过注解获取对象
     * T 接口 A 注解类型
     * @return
     */
    T getBeanWithAnnotation(Collection<T>  t, A a);

}
