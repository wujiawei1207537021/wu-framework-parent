package com.wu.framework.easy.pulsar.listener;

import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecords;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.pulsar.config.MethodPulsarListenerEndpoint;
import com.wu.framework.easy.pulsar.consumer.PulsarConsumerRecord;
import com.wu.framework.easy.pulsar.consumer.PulsarConsumerRecords;
import com.wu.framework.easy.pulsar.serialization.PulsarRecordSerialization;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class PulsarSingletonMessageListenerContainer implements SingletonMessageListenerContainer {

    private final Logger log = LoggerFactory.getLogger(PulsarSingletonMessageListenerContainer.class);
    // 异步线程池
    private final AsyncListenableTaskExecutor asyncListenableTaskExecutor = new ThreadPoolTaskExecutor();
    protected ListenerConsumer listenerConsumer;
    protected Consumer consumer;
    protected boolean running;
    private String beanName;
    private MethodPulsarListenerEndpoint endpoint;

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
        this.listenerConsumer = new PulsarListenerConsumer();
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
        try {
            getConsumer().close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
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

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }


    public MethodPulsarListenerEndpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(MethodPulsarListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    final class PulsarListenerConsumer implements ListenerConsumer {

        private final PulsarRecordSerialization serialization = new PulsarRecordSerialization();


        /**
         * Return whether the Runnable's operation is long-lived
         * ({@code true}) versus short-lived ({@code false}).
         * <p>In the former case, the task will not allocate a thread from the thread
         * pool (if any) but rather be considered as long-running background thread.
         * <p>This should be considered a hint. Of course TaskExecutor implementations
         * are free to ignore this flag and the SchedulingAwareRunnable interface overall.
         */
        @Override
        public boolean isLongLived() {
            return false;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {

            Consumer consumer = getConsumer();
            MethodPulsarListenerEndpoint endpoint = getEndpoint();
            Class<?>[] parameterTypes = endpoint.getMethod().getParameterTypes();

            Object bean = endpoint.getBean();

            while (isRunning()) {
                try {
                    if (!consumer.isConnected()) {
                        // 是否销毁容器
                        break;
                    }
                    // TODO 参数类型问题
                    // 判断第一个参数是什么
                    if (ConsumerRecord.class.isAssignableFrom(parameterTypes[0])) {

                        Message receive = consumer.receive();
                        if (!ObjectUtils.isEmpty(receive)) {
                            PulsarConsumerRecord record = new PulsarConsumerRecord(null, new String(receive.getData()), receive);
                            try {
                                final Object[] args = invokeArgs(parameterTypes, record, new ConsumerAcknowledgment(consumer, record));
                                endpoint.getMethod().invoke(bean, args);

                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (ConsumerRecords.class.isAssignableFrom(parameterTypes[0])) {
                        Messages messages = consumer.batchReceive();
                        if (messages.iterator().hasNext()) {
                            try {
                                PulsarConsumerRecords records = new PulsarConsumerRecords(messages);

                                // 判断是否添加自动消费参数
                                if (Arrays.stream(parameterTypes).anyMatch(Acknowledgment.class::isAssignableFrom)) {
                                    endpoint.getMethod().invoke(bean, records, new ConsumerBatchAcknowledgment(consumer, records));
                                } else {
                                    endpoint.getMethod().invoke(bean, records);
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (PulsarClientException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    private final class ConsumerBatchAcknowledgment implements Acknowledgment {

        protected Consumer consumer;
        protected PulsarConsumerRecords records;

        public ConsumerBatchAcknowledgment(Consumer consumer, PulsarConsumerRecords records) {
            this.consumer = consumer;
            this.records = records;
        }

        @Override
        public void acknowledge() {
            try {
                consumer.acknowledge(records.records());
            } catch (PulsarClientException e) {
                e.printStackTrace();
                log.error("ack 消费数据失败" + e.getMessage());
            }
        }
    }


    private final class ConsumerAcknowledgment implements Acknowledgment {

        protected Consumer consumer;
        protected PulsarConsumerRecord record;

        public ConsumerAcknowledgment(Consumer consumer, PulsarConsumerRecord record) {
            this.consumer = consumer;
            this.record = record;
        }

        @Override
        public void acknowledge() {
            try {
                consumer.acknowledge(record.message());
            } catch (PulsarClientException e) {
                e.printStackTrace();
                log.error("ack 消费数据失败" + e.getMessage());
            }
        }
    }
}


