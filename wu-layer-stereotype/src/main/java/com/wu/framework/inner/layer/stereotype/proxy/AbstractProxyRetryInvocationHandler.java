package com.wu.framework.inner.layer.stereotype.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * describe : 代理重试处理器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/12 21:51
 */
public abstract class AbstractProxyRetryInvocationHandler implements ProxyRetryInvocationHandler {

    private final Logger log = LoggerFactory.getLogger(AbstractProxyRetryInvocationHandler.class);

    /**
     * @param proxy     代理对象
     * @param method    代理方法
     * @param args      代理方法参数
     * @param retryTime 重试次数
     * @param throwable 异常
     * @param hasRetry  是否已经重试
     * @return 返回的执行结果
     * @throws Throwable
     */
    @Override
    public Object retryInvoke(Object proxy, Method method, Object[] args, int retryTime, Throwable throwable, boolean hasRetry) throws Throwable {
        try {
            Object invoke = invoke(proxy, method, args);
            afterRetryInvoke(hasRetry);
            return invoke;
        } catch (Throwable throwable1) {
            if (retryTime > 0) {
                return retryInvoke(proxy, method, args, retryTime - 1, throwable, true);
            }
            // 治愈失败
            log.warn("数据库治愈失败:{}", throwable);
            throw throwable;
        }
    }

    /**
     * 重试结束后操作
     *
     * @param hasRetry 是否重试
     */
    public abstract void afterRetryInvoke(boolean hasRetry);
}
