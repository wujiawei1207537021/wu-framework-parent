package com.wu.framework.inner.lazy.stereotype.proxy;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 策略方法 （方法标记） 下游具体class 进行代理
 * @date : 2021/4/8 6:55 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ProxyLazyStrategicApproach {


    /**
     * 代理class
     *
     * @return 声明的class
     */
    Class<?> proxyClass();

}