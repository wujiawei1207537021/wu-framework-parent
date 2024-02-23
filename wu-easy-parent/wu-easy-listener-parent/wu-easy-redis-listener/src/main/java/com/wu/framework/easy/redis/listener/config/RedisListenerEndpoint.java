package com.wu.framework.easy.redis.listener.config;

import com.wu.framework.easy.listener.core.config.ListenerEndpoint;

import java.util.Collection;

public interface RedisListenerEndpoint extends ListenerEndpoint {

    String getId();

    Collection<String> getTopics();


    int getConcurrency();

    String getConsumer();


    int getDatabase();

}
