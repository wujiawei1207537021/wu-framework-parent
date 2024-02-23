package com.wu.framework.easy.mysql.binlog.listener.listener;

import com.wu.framework.easy.listener.core.ListenerContainerRegistry;
import com.wu.framework.easy.listener.core.MessageListenerContainer;
import com.wu.framework.easy.mysql.binlog.listener.config.MethodMySQLBinlogBinlogListenerEndpoint;
import com.wu.framework.easy.mysql.binlog.listener.config.MySQLBinlogConcurrentListenerContainerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 注册表
 */
public class MySQLBinlogListenerEndpointRegistry implements ListenerContainerRegistry<MethodMySQLBinlogBinlogListenerEndpoint, MySQLBinlogConcurrentListenerContainerFactory> {


    private final boolean contextRefreshed = true;

    private final Map<String, MessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();


    /**
     * 注册监听容器
     *
     * @param endpoint         端点
     * @param factory          容器工厂
     * @param startImmediately 是否立即启动容器
     */
    @Override
    public void registerListenerContainer(MethodMySQLBinlogBinlogListenerEndpoint endpoint, MySQLBinlogConcurrentListenerContainerFactory factory, boolean startImmediately) {


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

    protected MessageListenerContainer createListenerContainer(MethodMySQLBinlogBinlogListenerEndpoint endpoint, MySQLBinlogConcurrentListenerContainerFactory factory) {

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
