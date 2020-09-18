package com.wu.framework.easy.stereotype.upsert.upsert.handler;


import com.wu.framework.easy.stereotype.upsert.dynamic.upsert.AbstractDynamicEasyUpsert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * description database 配置优先
 *
 * @author 吴佳伟
 * @date 2020/7/14 下午8:03
 */

@Slf4j
public class IUpsertHandler implements InvocationHandler {



    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;

    public IUpsertHandler(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List list = (List) args[0];
        if(ObjectUtils.isEmpty(list)){
            return false;
        }
        return abstractDynamicEasyUpsert.determineIEasyUpsert().upsert(list);
    }
}

