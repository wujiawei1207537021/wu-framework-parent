package com.wu.framework.inner.layer.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * description 代理工具
 *
 * @author 吴佳伟
 * @date 2023/06/15 21:12
 */
public class LazyAopProxyUtils {


    /**
     * 获取被代理类的Object
     *
     * @author wujiawei
     */
    public static Object getTarget(Object proxy) throws Exception {

        if (!AopUtils.isAopProxy(proxy)) {
            //不是代理对象
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }
    }

    /**
     * CGLIB方式被代理类的获取
     *
     * @author wujiawei
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }

    /**
     * JDK动态代理方式被代理类的获取
     *
     * @author wujiawei
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        return target;
    }


    /**
     * 获取被代理类
     *
     * @author wujiawei
     */
    public static Class<?> getTargetClass(Object proxy) throws Exception {

        if (!AopUtils.isAopProxy(proxy)) {
            //不是代理对象
            return proxy == null ? null : proxy.getClass();
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObjectClass(proxy);
        } else { //cglib
            return getCglibProxyTargetObjectClass(proxy);
        }
    }

    /**
     * CGLIB方式被代理类的获取
     *
     * @author wujiawei
     */
    private static Class<?> getCglibProxyTargetObjectClass(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
//        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        Class<?>[] proxiedInterfaces = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getProxiedInterfaces();
        return proxiedInterfaces[0];
    }

    /**
     * JDK动态代理方式被代理类的获取
     *
     * @author wujiawei
     */
    private static Class<?> getJdkDynamicProxyTargetObjectClass(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
//        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTargetClass();
        Class<?>[] proxiedInterfaces = ((ProxyFactory) advised.get(aopProxy)).getProxiedInterfaces();
        return proxiedInterfaces[0];
    }
}
