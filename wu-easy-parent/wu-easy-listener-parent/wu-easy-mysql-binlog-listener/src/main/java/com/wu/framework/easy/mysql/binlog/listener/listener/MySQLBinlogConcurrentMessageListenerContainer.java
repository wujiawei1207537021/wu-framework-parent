package com.wu.framework.easy.mysql.binlog.listener.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.wu.framework.easy.listener.core.ConcurrentMessageListenerContainer;
import com.wu.framework.easy.listener.core.GenericMessageListenerContainer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.config.SingletonListenerContainerFactory;
import com.wu.framework.easy.mysql.binlog.listener.config.MethodMySQLBinlogBinlogListenerEndpoint;
import com.wu.framework.easy.mysql.binlog.listener.config.TableAdapter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wujiawei
 */
public class MySQLBinlogConcurrentMessageListenerContainer<K, V> implements ConcurrentMessageListenerContainer<K, V>, GenericMessageListenerContainer<K, V> {

    protected List<MySQLBinlogSingletonMessageListenerContainer<K, V>> containerList = new ArrayList<>();
    private int concurrency = 1;
    private MethodMySQLBinlogBinlogListenerEndpoint endpoint;
    private BinaryLogClient binaryLogClient;
    private TableAdapter tableAdapter;

    public void setEndpoint(MethodMySQLBinlogBinlogListenerEndpoint endpoint) {
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

    public void setTableAdapter(TableAdapter tableAdapter) {
        this.tableAdapter = tableAdapter;
    }

    @Override
    public void setSingletonListenerContainerFactory(SingletonListenerContainerFactory containerFactory) {

    }

    public void setBinaryLogClient(BinaryLogClient binaryLogClient) {
        this.binaryLogClient = binaryLogClient;
    }


    @Override
    public SingletonMessageListenerContainer createContainer(int index) {
        Assert.notNull(binaryLogClient, "binlog连接对象不能为空");
        MySQLBinlogSingletonMessageListenerContainer<K, V> container = new MySQLBinlogSingletonMessageListenerContainer<K, V>(binaryLogClient, tableAdapter);
        container.setRunning(true);
        container.setBeanName(endpoint.getBeanName());
        container.setEndpoint(endpoint);
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
        containerList.forEach(MySQLBinlogSingletonMessageListenerContainer::stop);
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
