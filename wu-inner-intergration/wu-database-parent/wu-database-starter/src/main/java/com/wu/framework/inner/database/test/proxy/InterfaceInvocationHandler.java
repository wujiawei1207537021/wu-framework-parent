package com.wu.framework.inner.database.test.proxy;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 代理IInvocationHandlerTest
 * @date : 2020/6/25 下午8:31
 */
public class InterfaceInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("代理对象:"+ proxy);
        System.out.println("代理方法:" + method.getName());
        if (!ObjectUtils.isEmpty(args)) {
            System.out.println("代理参数:" + Arrays.toString(args));
        }

        return null;
    }
}
