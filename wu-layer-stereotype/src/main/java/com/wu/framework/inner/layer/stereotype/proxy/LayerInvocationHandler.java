package com.wu.framework.inner.layer.stereotype.proxy;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 自动代理 接口到invoke
 *
 * @LayerInvocationHandler(proxyInterface = DemoOperation.class)
 * public class DemoOperationProxy implements InvocationHandler {
 * @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 * }
 * <p>
 * <p>
 * }
 */

/**
 * 自动注入并代理 InvocationHandler 与 接口
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/15 10:52 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Indexed
public @interface LayerInvocationHandler {


    /**
     * description 代理接口类
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/7 下午3:15
     */
    Class proxyInterface();
}
