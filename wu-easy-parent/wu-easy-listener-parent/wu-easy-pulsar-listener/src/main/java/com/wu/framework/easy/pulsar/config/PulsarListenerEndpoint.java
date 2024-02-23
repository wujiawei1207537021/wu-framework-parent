package com.wu.framework.easy.pulsar.config;

import com.wu.framework.easy.listener.core.config.ListenerEndpoint;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.Collection;

public interface PulsarListenerEndpoint extends ListenerEndpoint {

    String getId();

    String getSubscriptionName();

    Collection<String> getTopics();

    SubscriptionType getSubscriptionType();

    int getConcurrency();
}
