package com.wu.framework.easy.mysql.listener;

import com.wu.framework.easy.listener.core.ConcurrentMessageListenerContainer;
import com.wu.framework.easy.listener.core.GenericMessageListenerContainer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.config.SingletonListenerContainerFactory;
import com.wu.framework.easy.mysql.listener.config.MethodMySQLListenerEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


public class MySQLConcurrentMessageListenerContainer<K, V> implements ConcurrentMessageListenerContainer<K, V>, GenericMessageListenerContainer<K, V> {

    protected List<MySQLSingletonMessageListenerContainer<K, V>> containerList = new ArrayList<>();
    private int concurrency = 1;
    private MethodMySQLListenerEndpoint endpoint;
    private LazyOperation operation;

    public void setEndpoint(MethodMySQLListenerEndpoint endpoint) {
        this.endpoint = endpoint;
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

    public void setOperation(LazyOperation operation) {
        this.operation = operation;
    }


    @Override
    public void start() {
        for (int i = 0; i < concurrency; i++) {
            for (String topic : endpoint.getTopics()) {
                final MySQLSingletonMessageListenerContainer<K, V> container = new MySQLSingletonMessageListenerContainer<K, V>();
                container.setRunning(true);
                container.setBeanName(endpoint.getBeanName());
                container.setStatement("select * from " + topic);
                container.setOperation(operation);
                container.setEndpoint(endpoint);
                containerList.add(container);
                container.start();
            }
            if (!ObjectUtils.isEmpty(endpoint.getStatement())) {
                final MySQLSingletonMessageListenerContainer<K, V> container = new MySQLSingletonMessageListenerContainer<K, V>();
                container.setRunning(true);
                container.setBeanName(endpoint.getBeanName());
                container.setStatement(endpoint.getStatement());
                container.setOperation(operation);
                container.setEndpoint(endpoint);
                containerList.add(container);
                container.start();
            }
        }
    }

    @Override
    public SingletonMessageListenerContainer createContainer(int index) {

        return null;
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
        containerList.forEach(MySQLSingletonMessageListenerContainer::stop);
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
