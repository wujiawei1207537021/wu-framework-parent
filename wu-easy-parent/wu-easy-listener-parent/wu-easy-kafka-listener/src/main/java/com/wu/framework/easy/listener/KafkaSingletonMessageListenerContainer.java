package com.wu.framework.easy.listener;

import com.wu.framework.easy.listener.config.MethodKafkaListenerEndpoint;
import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


public class KafkaSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(KafkaSingletonMessageListenerContainer.class);
    protected ListenerConsumer listenerConsumer;
    protected boolean running;
    private String beanName;


    private MethodKafkaListenerEndpoint endpoint;


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

        this.listenerConsumer = new KafkaListenerConsumer();

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


    public void setEndpoint(MethodKafkaListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }


    final class KafkaListenerConsumer implements ListenerConsumer {


        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {

        }

    }


}




