package com.wu.framework.easy.listener.core;

import com.wu.framework.easy.listener.core.config.ListenerContainerFactory;
import com.wu.framework.easy.listener.core.config.ListenerEndpoint;

/**
 * 侦听器容器注册表
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/17 6:00 下午
 */
public interface ListenerContainerRegistry<Endpoint extends ListenerEndpoint, Factory extends ListenerContainerFactory> {

    /**
     * 注册监听容器
     *
     * @param endpoint
     * @param factory
     * @param b
     */
    void registerListenerContainer(Endpoint endpoint, Factory factory, boolean startImmediately);
}
