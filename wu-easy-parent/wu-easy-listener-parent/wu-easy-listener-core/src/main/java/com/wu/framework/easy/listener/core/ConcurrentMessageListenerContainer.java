package com.wu.framework.easy.listener.core;

import com.wu.framework.easy.listener.core.config.SingletonListenerContainerFactory;

/**
 * describe : 并发消息侦听器容器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 6:15 下午
 */
public interface ConcurrentMessageListenerContainer<K, V> extends GenericMessageListenerContainer<K, V> {


    int getConcurrency();

    void setConcurrency(int concurrency);

    void setSingletonListenerContainerFactory(SingletonListenerContainerFactory containerFactory);

    /**
     * 创建容器
     *
     * @param index
     * @return
     */
    SingletonMessageListenerContainer createContainer(int index);

    @Override
    default void start() {
        for (int i = 0; i < getConcurrency(); i++) {
            final SingletonMessageListenerContainer<K, V> container = createContainer(i);
            container.start();
        }
    }

}
