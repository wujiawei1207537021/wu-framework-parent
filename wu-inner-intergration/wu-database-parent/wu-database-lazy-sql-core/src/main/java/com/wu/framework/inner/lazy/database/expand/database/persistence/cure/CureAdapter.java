package com.wu.framework.inner.lazy.database.expand.database.persistence.cure;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyRetryInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * describe : 治愈工厂
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/12 19:52
 */
public class CureAdapter {

    private final Logger log = LoggerFactory.getLogger(CureAdapter.class);

    private final List<Cure> cureList;


    public CureAdapter(List<Cure> cureList) {
        this.cureList = cureList;
    }

    /**
     * describe 治愈
     *
     * @param invocationHandler 动态代理对象
     * @param proxy             代理对象
     * @param method            代理方法
     * @param args              代理方法参数
     * @param retryTime         重试时间
     * @param throwable         异常
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/12 22:47
     **/
    public Object cure(ProxyRetryInvocationHandler invocationHandler, Object proxy, Method method, Object[] args, int retryTime, Throwable throwable) throws Throwable {
        if (retryTime <= 0) {
            throw throwable;
        }
        for (Cure cure : cureList) {
            if (cure.supports(throwable)) {
                try {
                    cure.cure(retryTime - 1, throwable);
                    return invocationHandler.retryInvoke(proxy, method, args, retryTime - 1, throwable, true);
                } catch (Throwable e) {
                    e.printStackTrace();
                    log.error("数据库执行治愈失败", e);
                }
            }
        }
        throw throwable;
    }

    /**
     * 根据异常信息治愈一次
     *
     * @param throwable
     * @throws Throwable
     */
    public void cureOne(Throwable throwable) throws Throwable {

        for (Cure cure : cureList) {
            if (cure.supports(throwable)) {
                try {
                    cure.cure(0, throwable);
                } catch (Throwable e) {
                    e.printStackTrace();
                    log.error("数据库执行one治愈失败", e);
                }
            }
        }
        throw throwable;
    }

}
