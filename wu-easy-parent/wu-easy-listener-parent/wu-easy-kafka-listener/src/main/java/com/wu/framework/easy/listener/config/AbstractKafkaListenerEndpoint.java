package com.wu.framework.easy.listener.config;


import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractKafkaListenerEndpoint implements KafkaListenerEndpoint {
    private final Collection<String> topics = new ArrayList<>();
    String containerFactory;
    private String id;
    private int concurrency = 1;
    // 消费者
    private String consumer;

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


    @Override
    public String getContainerFactory() {
        return containerFactory;
    }

    public void setContainerFactory(String containerFactory) {
        this.containerFactory = containerFactory;
    }
}
