package com.wu.framework.easy.stereotype.upsert.handler;


import com.wu.framework.easy.stereotype.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.inner.layer.stereotype.DefaultProxyMethod;
import com.wu.framework.inner.layer.stereotype.ProxyMethodFunction;
import org.springframework.util.ObjectUtils;

/**
 * description database 配置优先
 *
 * @author Jia wei Wu
 * @date 2020/7/14 下午8:03
 */

public class IUpsertHandler implements DefaultProxyMethod {


    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;

    public IUpsertHandler(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }

    @Override
    public Object invoke(Object proxy, ProxyMethodFunction proxyMethodFunction, Object[] args) throws Exception {
        Object arg;
        if (ObjectUtils.isEmpty(args) || null == (arg = args[0])) {
            return false;
        }
        if (arg instanceof Object[]) {
            Object[] objects = (Object[]) arg;
            for (Object o : objects) {
                abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(o);
            }
        } else {
            abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(arg);
        }
        return true;
    }
}

