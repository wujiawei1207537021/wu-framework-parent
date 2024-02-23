package com.wu.framework.easy.redis.listener;

import com.alibaba.fastjson.JSON;
import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecordType;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecords;
import com.wu.framework.easy.redis.listener.ack.RedisAcknowledgment;
import com.wu.framework.easy.redis.listener.config.MethodRedisListenerEndpoint;
import com.wu.framework.easy.redis.listener.consumer.RedisConsumerRecord;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RedisSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(RedisSingletonMessageListenerContainer.class);
    protected ListenerConsumer listenerConsumer;
    @Setter
    protected boolean running;

    @Setter
    private String beanName;


    @Setter
    private StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection;


    @Setter
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
        SimpleAsyncTaskExecutor consumerExecutor = new SimpleAsyncTaskExecutor(beanName + "-redis-");
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
        statefulRedisPubSubConnection.close();
        this.running = false;
        log.info("close redis client with " +
                " consumer " + endpoint.getConsumer() +
                " topics " + endpoint.getTopics());
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


    private String getBeanName() {
        return beanName;
    }


    /**
     * 数据监听
     */
    @Setter
    final class RedisListenerStatementConsumer implements ListenerConsumer {
        private boolean running;



        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {


            final Method endpointMethod = endpoint.getMethod();
            final Object bean = endpoint.getBean();
            final Class<?>[] parameterTypes = endpointMethod.getParameterTypes();


            boolean isConsumerRecords = null != includeParameterTypes(parameterTypes, ConsumerRecords.class);
            boolean isConsumerRecord = null != includeParameterTypes(parameterTypes, ConsumerRecord.class);

            ConsumerRecordType consumerRecordType = consumerRecord(endpointMethod);

            Assert.notNull(consumerRecordType, "监听器接收方法不能为空");
            Class<?> payloadType = consumerRecordType.getPayloadType();
            RedisPubSubListener<String, String> jedisPubSub = new RedisPubSubListener<String, String>() {
                /**
                 * Message received from a channel subscription.
                 *
                 * @param channel Channel.
                 * @param message Message.
                 */
                @Override
                public void message(String channel, String message) {

                    Object record = null;
                    if (!ObjectUtils.isEmpty(message)) {
                        if (isConsumerRecord) {
                            try {
                                record = new RedisConsumerRecord(null, JSON.parseObject(message, payloadType));
                            }catch (Exception e){
                                e.printStackTrace();
                                throw new RuntimeException(MessageFormat.format("错误的数据格式解析:{0}】", message));
                            }
                        } else {
                            //  序列化多个对象
                            record = new ConsumerRecords() {

                                /**
                                 * Returns an iterator over elements of type {@code T}.
                                 *
                                 * @return an Iterator.
                                 */
                                @Override
                                public Iterator iterator() {
                                    return Stream.of(message).map(s -> {
                                        return new ConsumerRecord() {
                                            @Override
                                            public Object schema() {
                                                return null;
                                            }

                                            @Override
                                            public Object payload() {
                                                try {
                                                    return JSON.parseObject(message, payloadType);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    throw new RuntimeException(MessageFormat.format("错误的数据格式解析:{0}】", message));
                                                }
                                            }
                                        };
                                    }).collect(Collectors.toList()).iterator();
                                }
                            };
                        }

                    }
                    try {
                        final Object[] args = invokeArgs(parameterTypes, record, new RedisAcknowledgment(record));
                        endpointMethod.invoke(bean, args);

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                /**
                 * Message received from a pattern subscription.
                 *
                 * @param pattern Pattern
                 * @param channel Channel
                 * @param message Message
                 */
                @Override
                public void message(String pattern, String channel, String message) {
                    // 订阅 pattern 处理

                }

                /**
                 * Subscribed to a channel.
                 *
                 * @param channel Channel
                 * @param count   Subscription count.
                 */
                @Override
                public void subscribed(String channel, long count) {
                    // 通道订阅次数

                }

                /**
                 * Subscribed to a pattern.
                 *
                 * @param pattern Pattern.
                 * @param count   Subscription count.
                 */
                @Override
                public void psubscribed(String pattern, long count) {
                    // 订阅 pattern 次数处理
                }

                /**
                 * Unsubscribed from a channel.
                 *
                 * @param channel Channel
                 * @param count   Subscription count.
                 */
                @Override
                public void unsubscribed(String channel, long count) {
                    // 取消订阅 通道 处理
                }

                /**
                 * Unsubscribed from a pattern.
                 *
                 * @param pattern Channel
                 * @param count   Subscription count.
                 */
                @Override
                public void punsubscribed(String pattern, long count) {
                    // 取消订阅 pattern 处理
                }

            };
            statefulRedisPubSubConnection.addListener(jedisPubSub);
            RedisPubSubAsyncCommands<String, String> redisPubSubAsyncCommands = statefulRedisPubSubConnection.async();
            redisPubSubAsyncCommands.subscribe(endpoint.getTopics().toArray(new String[0]));

        }

    }


}




