package com.wu.framework.inner.lazy.stereotype.proxy;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 策略方法 （方法标记） 上游代理DML、DQL、DDL
 * @date : 2021/4/8 6:55 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ProxyLazyBaseLayerStrategicApproach {


    /**
     * 代理class name
     *
     * @return
     */
    String proxyClassName() default "";

}