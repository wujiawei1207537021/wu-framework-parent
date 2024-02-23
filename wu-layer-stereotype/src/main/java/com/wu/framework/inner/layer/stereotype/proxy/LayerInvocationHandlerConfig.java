package com.wu.framework.inner.layer.stereotype.proxy;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * description  层代理配置  //TODO
 *
 * @author Jia wei Wu
 * @date 2021/4/7 下午3:14
 */
public class LayerInvocationHandlerConfig implements InitializingBean {


    private final ConfigurableBeanFactory configurableBeanFactory;
    private final List<InvocationHandler> invocationHandlerList;

    public LayerInvocationHandlerConfig(ConfigurableBeanFactory configurableBeanFactory, List<InvocationHandler> invocationHandlerList) {
        this.configurableBeanFactory = configurableBeanFactory;
        this.invocationHandlerList = invocationHandlerList;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 扫描所有包含 注解@ProxyStrategicApproach的接口

        // 没有aop  需要通过 BeanFactoryPostProcessor 的实现类注册才可(无法注入bean)
        for (InvocationHandler invocationHandler : invocationHandlerList) {
            LayerInvocationHandler layerProxy = AnnotatedElementUtils.findMergedAnnotation(invocationHandler.getClass(), LayerInvocationHandler.class);
            if (null != layerProxy) {
                configurableBeanFactory.registerSingleton(layerProxy.proxyInterface().getSimpleName(),
                        Proxy.newProxyInstance(layerProxy.proxyInterface().getClassLoader(), new Class[]{layerProxy.proxyInterface()}, invocationHandler));
            }
        }
    }
}
