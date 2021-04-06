package com.wu.framework.inner.layer.stereotype;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 修复hashCode 、 toString 方法空值问题
 * @date : 2021/4/6 7:29 下午
 */
public interface DefaultProxyMethod extends InvocationHandler {


    Object invoke(Object proxy, ProxyMethodFunction proxyMethodFunction, Object[] args) throws Throwable;

    @Override
    default Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "toString":
                return method.toString();
            case "hashCode":
                return method.hashCode();
            default: return invoke(proxy, defaultMethod -> defaultMethod, args);
        }
    }


    default Object defaultMethod(Method defaultMethod){
        switch (defaultMethod.getName()) {
            case "toString":
                return defaultMethod.toString();
            case "hashCode":
                return defaultMethod.hashCode();
            default: return defaultMethod;
        }
    }


}
