package com.wu.framework.inner.layer.stereotype.proxy;

import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.layer.stereotype.LayerMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.lang.reflect.InvocationHandler;

/**
 * @describe: 策略方法
 * @author : Jia wei Wu
 * @date : 2021/4/8 6:55 下午
 * @version : 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerMethod
@LayerClass
@Component
public @interface ProxyStrategicApproach {

    String methodName() default "miss";


    /**
     * @param
     * @return
     * @describe 代理类
     * @author Jia wei Wu
     * @date 2021/3/28 10:16 下午
     **/
    Class<? extends InvocationHandler> proxyClass() default InvocationHandler.class;
}
