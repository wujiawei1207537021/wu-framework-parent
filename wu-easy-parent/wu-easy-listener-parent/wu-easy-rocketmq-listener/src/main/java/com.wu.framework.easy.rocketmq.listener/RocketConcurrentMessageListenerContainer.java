package com.wu.framework.easy.rocketmq.listener;

import com.wu.framework.easy.listener.core.ConcurrentMessageListenerContainer;
import com.wu.framework.easy.listener.core.GenericMessageListenerContainer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.config.SingletonListenerContainerFactory;
import com.wu.framework.easy.rocketmq.listener.config.MethodRocketListenerEndpoint;
import com.wu.framework.inner.intergration.rocketmq.RocketProperties;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;

import java.util.ArrayList;
import java.util.List;


public class RocketConcurrentMessageListenerContainer<K, V> implements ConcurrentMessageListenerContainer<K, V>, GenericMessageListenerContainer<K, V> {

    protected List<RocketSingletonMessageListenerContainer<K, V>> containerList = new ArrayList<>();
    private int concurrency = 1;
    private MethodRocketListenerEndpoint endpoint;

    private RocketProperties rocketProperties;


    public void setEndpoint(MethodRocketListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void setRocketProperties(RocketProperties RocketProperties) {
        this.rocketProperties = RocketProperties;
    }

    @Override
    public int getConcurrency() {
        return concurrency;
    }

    @Override
    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    @Override
    public void setSingletonListenerContainerFactory(SingletonListenerContainerFactory containerFactory) {

    }


    @Override
    public SingletonMessageListenerContainer createContainer(int index) {
        final RocketSingletonMessageListenerContainer<K, V> container = new RocketSingletonMessageListenerContainer<K, V>();
        container.setRunning(true);
        container.setBeanName(endpoint.getBeanName());
        container.setEndpoint(endpoint);
        // 创建 LitePullConsumer 对象
        DefaultLitePullConsumer pullConsumer = new DefaultLitePullConsumer();

        // 批量数据大小
        pullConsumer.setPullBatchSize(endpoint.consumeMessageBatchMaxSize());
        // 订阅者
        pullConsumer.setConsumerGroup(endpoint.getConsumer());
        // 主题
        for (String topic : endpoint.getTopics()) {
            try {
                pullConsumer.subscribe(topic, "*");
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }
        // 集群模式
        pullConsumer.setMessageModel(endpoint.messageModel());
        // namesrvAddr
        pullConsumer.setNamesrvAddr(rocketProperties.getNamesrvAddr());

        pullConsumer.setConsumeFromWhere(endpoint.consumeFromWhere());

        pullConsumer.setAutoCommit(false);
        container.setPullConsumer(pullConsumer);

        containerList.add(container);
        return container;
    }

    /**
     * Stop this component, typically in a synchronous fashion, such that the component is
     * fully stopped upon return of this method. Consider implementing {@link SmartLifecycle}
     * and its {@code stop(Runnable)} variant when asynchronous stop behavior is necessary.
     * <p>Note that this stop notification is not guaranteed to come before destruction:
     * On regular shutdown, {@code Lifecycle} beans will first receive a stop notification
     * before the general destruction callbacks are being propagated; however, on hot
     * refresh during a context's lifetime or on aborted refresh attempts, a given bean's
     * destroy method will be called without any consideration of stop signals upfront.
     * <p>Should not throw an exception if the component is not running (not started yet).
     * <p>In the case of a container, this will propagate the stop signal to all components
     * that apply.
     *
     * @see SmartLifecycle#stop(Runnable)
     * @see DisposableBean#destroy()
     */
    @Override
    public void stop() {
        containerList.forEach(RocketSingletonMessageListenerContainer::stop);
    }

    /**
     * Check whether this component is currently running.
     * <p>In the case of a container, this will return {@code true} only if <i>all</i>
     * components that apply are currently running.
     *
     * @return whether the component is currently running
     */
    @Override
    public boolean isRunning() {
        return true;
    }

}
