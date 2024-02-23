package com.wu.framework.easy.rocketmq.listener.config;

import com.wu.framework.easy.listener.core.config.ListenerEndpoint;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.Collection;

public interface RocketListenerEndpoint extends ListenerEndpoint {

    String getId();

    Collection<String> getTopics();


    int getConcurrency();

    String getConsumer();


    /**
     * 消息模式
     *
     * @return
     */
    MessageModel messageModel();

    /**
     * 批量消费消息
     *
     * @return
     */
    int consumeMessageBatchMaxSize();


    /**
     * 从哪里消费
     *
     * @return
     */
    ConsumeFromWhere consumeFromWhere();


}
