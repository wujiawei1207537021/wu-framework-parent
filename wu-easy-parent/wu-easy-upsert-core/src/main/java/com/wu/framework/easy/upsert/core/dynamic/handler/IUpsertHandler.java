package com.wu.framework.easy.upsert.core.dynamic.handler;



import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * description database 配置优先
 *
 * @author Jia wei Wu
 * @date 2020/7/14 下午8:03
 */

public class IUpsertHandler implements InvocationHandler {


    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;

    public IUpsertHandler(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getParameterCount() != 0) {
            Object arg;
            if (ObjectUtils.isEmpty(args) || null == (arg = args[0])) {
                return false;
            }
            if (arg instanceof Object[]) {
                Object[] objects = (Object[]) arg;
                abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(objects);
            } else {
                abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(arg);
            }
            return true;
        } else {
            return method.invoke(this,args);
        }
    }


}

