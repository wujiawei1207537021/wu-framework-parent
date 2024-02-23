package com.wu.framework.easy.redis.listener;

import com.wu.framework.easy.listener.core.ConcurrentMessageListenerContainer;
import com.wu.framework.easy.listener.core.GenericMessageListenerContainer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.config.SingletonListenerContainerFactory;
import com.wu.framework.easy.redis.listener.config.MethodRedisListenerEndpoint;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


public class RedisConcurrentMessageListenerContainer<K, V> implements ConcurrentMessageListenerContainer<K, V>, GenericMessageListenerContainer<K, V> {

    protected List<RedisSingletonMessageListenerContainer<K, V>> containerList = new ArrayList<>();
    private int concurrency = 1;
    @Setter
    private MethodRedisListenerEndpoint endpoint;


    @Setter
    private RedisClient redisClient;
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
        final RedisSingletonMessageListenerContainer<K, V> container = new RedisSingletonMessageListenerContainer<K, V>();
        container.setRunning(true);
        container.setBeanName(endpoint.getBeanName());
        container.setEndpoint(endpoint);
        StatefulRedisPubSubConnection<String, String> stringStringStatefulRedisPubSubConnection = redisClient.connectPubSub();
        container.setStatefulRedisPubSubConnection(stringStringStatefulRedisPubSubConnection);
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
        containerList.forEach(RedisSingletonMessageListenerContainer::stop);
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
