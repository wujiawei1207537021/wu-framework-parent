package com.wu.framework.easy.rocketmq.listener.config;


import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractRocketListenerEndpoint implements RocketListenerEndpoint {
    private final Collection<String> topics = new ArrayList<>();


    private String id;

    private int concurrency = 1;

    // 消费者
    private String consumer;

    private MessageModel messageMode = MessageModel.BROADCASTING;

    private int consumeMessageBatchMaxSize = 10;

    private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;


    @Override
    public Collection<String> getTopics() {
        return topics;
    }

    public void setTopics(String... topics) {
        Assert.notNull(topics, "'topics' must not be null ");
        this.topics.clear();
        this.topics.addAll(Arrays.asList(topics));
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    @Override
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }


    public void setMessageMode(MessageModel messageMode) {
        this.messageMode = messageMode;
    }

    public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    /**
     * 消息模式
     *
     * @return
     */
    @Override
    public MessageModel messageModel() {
        return messageMode;
    }

    /**
     * 批量消费消息
     *
     * @return
     */
    @Override
    public int consumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    /**
     * 从哪里消费
     *
     * @return
     */
    @Override
    public ConsumeFromWhere consumeFromWhere() {
        return consumeFromWhere;
    }

    public void setConsumeFromWhere(ConsumeFromWhere consumeFromWhere) {
        this.consumeFromWhere = consumeFromWhere;
    }

}
