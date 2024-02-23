package com.wu.framework.easy.rocketmq.listener;

import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.rocketmq.listener.ack.RocketAcknowledgment;
import com.wu.framework.easy.rocketmq.listener.ack.RocketBatchAcknowledgment;
import com.wu.framework.easy.rocketmq.listener.config.MethodRocketListenerEndpoint;
import com.wu.framework.easy.rocketmq.listener.consumer.RocketConsumerRecords;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class RocketSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(RocketSingletonMessageListenerContainer.class);
    protected ListenerConsumer listenerConsumer;
    protected boolean running;

    private String beanName;


    private MethodRocketListenerEndpoint endpoint;

    private LitePullConsumer pullConsumer;


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

        this.listenerConsumer = new RocketListenerStatementConsumer();

        setRunning(true);
        String beanName = getBeanName();
        SimpleAsyncTaskExecutor consumerExecutor = new SimpleAsyncTaskExecutor(beanName + "-rocket-");
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
        pullConsumer.shutdown();
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


    public void setEndpoint(MethodRocketListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void setPullConsumer(LitePullConsumer pullConsumer) {
        this.pullConsumer = pullConsumer;
    }

    /**
     * 数据监听
     */
    final class RocketListenerStatementConsumer implements ListenerConsumer {


        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {

            final Method endpointMethod = endpoint.getMethod();
            final Object bean = endpoint.getBean();
            final Class<?>[] parameterTypes = endpointMethod.getParameterTypes();

            try {
                pullConsumer.setAutoCommit(false);
                pullConsumer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
            while (running) {
                final List<MessageExt> messageExtList = pullConsumer.poll();
                RocketConsumerRecords records = new RocketConsumerRecords();

                final Object[] args =
                        invokeArgs(
                                parameterTypes,
                                records, messageExtList,
                                new RocketBatchAcknowledgment(pullConsumer),
                                new RocketAcknowledgment(pullConsumer));
                try {
                    endpointMethod.invoke(bean, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }

    }


}




