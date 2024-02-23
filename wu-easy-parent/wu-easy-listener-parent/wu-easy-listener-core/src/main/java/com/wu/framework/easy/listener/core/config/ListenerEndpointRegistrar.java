package com.wu.framework.easy.listener.core.config;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * describe : 侦听器端点注册器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 5:59 下午
 */
public interface ListenerEndpointRegistrar<Endpoint extends ListenerEndpoint, Factory extends ListenerContainerFactory>
        extends BeanFactoryAware, InitializingBean {


    /**
     * 注册 端点
     *
     * @param endpoint
     * @param factory
     */
    void registerEndpoint(Endpoint endpoint, Factory factory);


}
