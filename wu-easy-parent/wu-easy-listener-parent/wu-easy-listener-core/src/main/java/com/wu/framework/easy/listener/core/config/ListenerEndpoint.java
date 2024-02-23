package com.wu.framework.easy.listener.core.config;

import java.lang.reflect.Method;

/**
 * describe : 监听端点
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 5:34 下午
 */
public interface ListenerEndpoint {
    String getBeanName();


    Object getBean();


    Method getMethod();
}
