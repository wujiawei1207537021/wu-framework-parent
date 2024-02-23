package com.wu.framework.inner.layer.stereotype.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * describe: 方法代理重试
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2023/1/12 21:49
 */
public interface ProxyRetryInvocationHandler extends InvocationHandler {

    /**
     * describe
     *
     * @param proxy     代理对象
     * @param method    代理方法
     * @param args      代理方法参数
     * @param retryTime 重试次数
     * @param throwable 异常
     * @param hasRetry  是否已经重拾
     * @return 返回的执行结果
     * @return
     * @throws Throwable
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/12 21:55
     */
    Object retryInvoke(Object proxy, Method method, Object[] args, int retryTime, Throwable throwable, boolean hasRetry) throws Throwable;


}
