package com.wu.framework.easy.listener.config;

import com.wu.framework.easy.listener.core.config.ListenerEndpoint;

import java.util.Collection;

public interface KafkaListenerEndpoint extends ListenerEndpoint {

    String getId();

    Collection<String> getTopics();


    int getConcurrency();

    String getConsumer();


    String getContainerFactory();
}
