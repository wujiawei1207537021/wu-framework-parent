package com.wu.framework.inner.database.stereotype;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.lang.reflect.InvocationHandler;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: methodStrategy
 * @date : 2020/7/3 下午10:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Indexed
public @interface ProxyLayerMethodStrategy {


    String methodName();

    /**
     * @param
     * @return
     * @describe 代理类
     * @author Jia wei Wu
     * @date 2021/3/28 10:16 下午
     **/
    Class<? extends InvocationHandler> proxyClass();


}
