package com.wu.framework.easy.redis.listener;

import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.redis.listener.ack.RedisAcknowledgment;
import com.wu.framework.easy.redis.listener.config.MethodRedisListenerEndpoint;
import com.wu.framework.easy.redis.listener.consumer.RedisConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class RedisSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(RedisSingletonMessageListenerContainer.class);
    protected ListenerConsumer listenerConsumer;
    protected boolean running;

    private String beanName;


    private Jedis jedis;


    private MethodRedisListenerEndpoint endpoint;


    // 异步线程池
//    private AsyncListenableTaskExecutor asyncListenableTaskExecutor = new ThreadPoolTaskExecutor();

    /**
     * Start this component.
     * <p>Should not throw an exception if the component is already running.
     * <p>In the case of a container, this will propagate the start signal to all
     * components that apply.
     *
     * @see SmartLifecycle#isAutoStartup()
     */
    @Override
    public void start() {

        this.listenerConsumer = new RedisListenerStatementConsumer();

        setRunning(true);
        String beanName = getBeanName();
        SimpleAsyncTaskExecutor consumerExecutor = new SimpleAsyncTaskExecutor(beanName + "-C-");
        consumerExecutor.submitListenable(this.listenerConsumer);
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
        jedis.close();
        this.running = false;
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
        return running;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    private String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


    public void setEndpoint(MethodRedisListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }


    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 数据监听
     */
    final class RedisListenerStatementConsumer implements ListenerConsumer {


        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {


            if (endpoint.getDatabase() >= 0 && endpoint.getDatabase() <= 15) {
                jedis.select(endpoint.getDatabase());
            }

            final Method endpointMethod = endpoint.getMethod();
            final Object bean = endpoint.getBean();
            final Class<?>[] parameterTypes = endpointMethod.getParameterTypes();


            final JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {

                    if (!ObjectUtils.isEmpty(message)) {

                        RedisConsumerRecord record = new RedisConsumerRecord(null, message);

                        //  序列化多个对象

                        try {
                            final Object[] args = invokeArgs(parameterTypes, record, new RedisAcknowledgment(jedis, record));
                            endpointMethod.invoke(bean, args);

                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    super.onMessage(channel, message);
                }

                @Override
                public void onPMessage(String pattern, String channel, String message) {
                    super.onPMessage(pattern, channel, message);
                }
            };
            jedis.subscribe(jedisPubSub, endpoint.getTopics().toArray(new String[0]));

        }

    }


}




