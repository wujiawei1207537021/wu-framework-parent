package com.wu.framework.easy.listener.core.config;

import com.wu.framework.easy.listener.core.MessageListenerContainer;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.regex.Pattern;


public interface ListenerContainerFactory<C extends MessageListenerContainer, Endpoint extends ListenerEndpoint> extends BeanFactoryAware {

    C createListenerContainer(Endpoint endpoint);


    C createContainer(String... topics);


    C createContainer(Pattern topicPattern);

}