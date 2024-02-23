package com.wu.framework.easy.listener.core;

public interface ProviderTemplate<T, ID> {

    /**
     * 数据发送
     *
     * @param topic   发送的主题、通道
     * @param message 发送的数据
     * @param <T>     数据范型
     * @return ID 返回数据
     */
    <T> ID send(String topic, T message);

}
