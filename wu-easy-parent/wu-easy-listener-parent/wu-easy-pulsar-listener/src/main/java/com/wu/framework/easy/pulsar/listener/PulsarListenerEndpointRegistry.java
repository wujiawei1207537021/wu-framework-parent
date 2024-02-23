package com.wu.framework.easy.pulsar.listener;

import com.wu.framework.easy.listener.core.ListenerContainerRegistry;
import com.wu.framework.easy.listener.core.MessageListenerContainer;
import com.wu.framework.easy.pulsar.config.MethodPulsarListenerEndpoint;
import com.wu.framework.easy.pulsar.config.PulsarConcurrentListenerContainerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 注册表
 */
public class PulsarListenerEndpointRegistry implements ListenerContainerRegistry<MethodPulsarListenerEndpoint, PulsarConcurrentListenerContainerFactory> {


    private final boolean contextRefreshed = true;

    private final Map<String, MessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();

    /**
     * 注册监听容器
     *
     * @param endpoint
     * @param factory
     * @param startImmediately
     */


    /**
     * 注册监听容器
     *
     * @param endpoint
     * @param factory
     * @param startImmediately
     */
    @Override
    public void registerListenerContainer(MethodPulsarListenerEndpoint endpoint, PulsarConcurrentListenerContainerFactory factory, boolean startImmediately) {


        Assert.notNull(endpoint, "Endpoint must not be null");
        Assert.notNull(factory, "Factory must not be null");

        String id = endpoint.getId();
        Assert.hasText(id, "Endpoint id must not be empty");
        synchronized (this.listenerContainers) {
            Assert.state(!this.listenerContainers.containsKey(id),
                    "Another endpoint is already registered with id '" + id + "'");
            MessageListenerContainer container = createListenerContainer(endpoint, factory);
            this.listenerContainers.put(id, container);
            if (startImmediately) {
                startIfNecessary(container);
            }
        }
    }

    protected MessageListenerContainer createListenerContainer(MethodPulsarListenerEndpoint endpoint,
                                                               PulsarConcurrentListenerContainerFactory factory) {

        MessageListenerContainer listenerContainer = factory.createListenerContainer(endpoint);

        if (listenerContainer instanceof InitializingBean) {
            try {
                ((InitializingBean) listenerContainer).afterPropertiesSet();
            } catch (Exception ex) {
                throw new BeanInitializationException("Failed to initialize record listener container", ex);
            }
        }

        int containerPhase = listenerContainer.getPhase();

        return listenerContainer;
    }

    private void startIfNecessary(MessageListenerContainer listenerContainer) {
        if (this.contextRefreshed || listenerContainer.isAutoStartup()) {
            listenerContainer.start();
        }
    }


}
