package com.wu.framework.inner.layer.stereotype.proxy;

import com.wu.framework.inner.layer.stereotype.LayerMethod;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.lang.reflect.InvocationHandler;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 策略方法
 * @date : 2021/4/8 6:55 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerMethod
public @interface ProxyStrategicApproach {

    @Deprecated
    String methodName() default "miss";


    /**
     * @param
     * @return
     * @describe 代理类
     * @author Jia wei Wu
     * @date 2021/3/28 10:16 下午
     **/
    Class<?> proxyClass() default InvocationHandler.class;
}
