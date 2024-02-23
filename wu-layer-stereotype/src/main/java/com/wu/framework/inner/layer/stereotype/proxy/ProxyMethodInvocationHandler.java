package com.wu.framework.inner.layer.stereotype.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * describe: 方法代理
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2023/1/12 21:47
 */
public interface ProxyMethodInvocationHandler extends InvocationHandler {


    Object invoke(Object proxy, ProxyMethodFunction proxyMethodFunction, Object[] args) throws Throwable;

    @Override
    default Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法上的实例对象 优先

        // 类上的默认对象


        switch (method.getName()) {
            case "toString":
                return method.toString();
            case "hashCode":
                return method.hashCode();
            default:
                return invoke(proxy, defaultMethod -> defaultMethod, args);
        }
    }


}
