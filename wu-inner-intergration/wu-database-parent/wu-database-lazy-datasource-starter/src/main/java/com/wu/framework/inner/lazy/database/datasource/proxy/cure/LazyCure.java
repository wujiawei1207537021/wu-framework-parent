package com.wu.framework.inner.lazy.database.datasource.proxy.cure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * description 持久层治愈
 *
 * @author Jia wei Wu
 * @date 2023/01/11 17:12
 */
public interface LazyCure {


    /**
     * 判断当前能否治愈此异常
     *
     * @param throwable 异常信息
     * @return
     */
    boolean supports(Throwable throwable);

    /**
     * 治愈
     *
     * @param invocationHandler 动态代理对象
     * @param proxy             代理对象
     * @param method            代理方法
     * @param args              代理参数
     * @param retryTime
     * @param throwable         异常信息
     */
    void cure(InvocationHandler invocationHandler, Object proxy, Method method, Object[] args, int retryTime, Throwable throwable) throws Throwable;

}