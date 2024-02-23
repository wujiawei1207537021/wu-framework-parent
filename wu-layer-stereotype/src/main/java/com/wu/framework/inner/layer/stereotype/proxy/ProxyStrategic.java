package com.wu.framework.inner.layer.stereotype.proxy;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.lang.reflect.InvocationHandler;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * 策略代理
 * @date : 2021/4/8 6:55 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ProxyStrategic {


    /**
     * describe 代理类
     *
     * @author Jia wei Wu
     * @date 2021/3/28 10:16 下午
     **/
    Class<? extends InvocationHandler> proxyClass() default DefaultProxyMethodInvocationHandler.class;
}
