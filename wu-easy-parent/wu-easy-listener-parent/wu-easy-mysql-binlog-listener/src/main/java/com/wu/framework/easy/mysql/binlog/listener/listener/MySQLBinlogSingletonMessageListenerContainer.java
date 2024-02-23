package com.wu.framework.easy.mysql.binlog.listener.listener;


import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecordType;
import com.wu.framework.easy.listener.stereotype.mysql.binlog.EasyMySQLBinlogListener;
import com.wu.framework.easy.mysql.binlog.listener.ack.MySQLBinlogAcknowledgment;
import com.wu.framework.easy.mysql.binlog.listener.config.MethodMySQLBinlogBinlogListenerEndpoint;
import com.wu.framework.easy.mysql.binlog.listener.config.TableAdapter;
import com.wu.framework.easy.mysql.binlog.listener.consumer.BinlogConsumerRecord;
import com.wu.framework.easy.mysql.binlog.listener.serialization.TableInfo;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author wujiawei
 */
public class MySQLBinlogSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(MySQLBinlogSingletonMessageListenerContainer.class);
    private final BinaryLogClient binaryLogClient;
    private final TableAdapter tableAdapter;
    protected ListenerConsumer listenerConsumer;
    protected boolean running;
    private String beanName;
    private MethodMySQLBinlogBinlogListenerEndpoint endpoint;


    public MySQLBinlogSingletonMessageListenerContainer(BinaryLogClient binaryLogClient, TableAdapter tableAdapter) {
        this.binaryLogClient = binaryLogClient;
        this.tableAdapter = tableAdapter;
    }


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
        this.listenerConsumer = new MySQLListenerConsumer();
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


    public void setEndpoint(MethodMySQLBinlogBinlogListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }


    final class MySQLListenerConsumer implements ListenerConsumer {

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

            binaryLogClient.registerEventListener(event -> {
                EventData data = event.getData();
                if (data instanceof TableMapEventData) {
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    long tableId = tableMapEventData.getTableId();
                    boolean exists = tableAdapter.existsTableId(tableId);
                    String database = tableMapEventData.getDatabase();
                    String table = tableMapEventData.getTable();
                    if (!exists) {
                        tableAdapter.cacheTable(tableId, database, table);
                    }
                }
            });


            EasyMySQLBinlogListener.Pattern[] patternList = endpoint.getPattern();
            for (EasyMySQLBinlogListener.Pattern pattern : patternList) {
                if (EasyMySQLBinlogListener.Pattern.UPDATE.equals(pattern)) {
                    BinaryLogClient.EventListener eventListener = event -> {
                        EventHeader header = event.getHeader();
                        if (EventHeaderV4.class.isAssignableFrom(header.getClass())) {
                            EventHeaderV4 eventHeaderV4 = (EventHeaderV4) header;
                            long position = eventHeaderV4.getPosition();
                        }
                        EventData data = event.getData();
                        if (data instanceof UpdateRowsEventData) {
                            UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;

                            // 获取更新字段
                            long tableId = updateRowsEventData.getTableId();
                            TableInfo table = tableAdapter.getTable(tableId);
                            String schema = endpoint.getSchema();
                            Collection<String> tables = endpoint.getTables();
                            // 正则校验数据库
                            if (ObjectUtils.isEmpty(schema) || !Pattern.matches(schema, table.getSchema())) {
                                return;
                            }
                            if (ObjectUtils.isEmpty(schema) || ObjectUtils.isEmpty(tables)
                                    || !tables.contains(table.getTableName())) {
                                return;
                            }

                            Map<Long, LazyColumn> ordinalPositionMap = table.getOrdinalPositionMap();


                            for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                                // 多个数据监听
                                Map<String, Object> beanMap = new HashMap<>();
                                for (int i = 0; i < row.getValue().length; i++) {
                                    Serializable serializable = row.getValue()[i];
                                    LazyColumn lazyColumn = ordinalPositionMap.get(i + 1L);
                                    if (null == lazyColumn) {
                                        log.warn("无法查询到表字段，请确认是否拥有表查看权限");
                                    }
                                    beanMap.put(lazyColumn.getColumnName(), serializable);
                                }

                                Method method = endpoint.getMethod();
                                Class<?>[] parameterTypes = method.getParameterTypes();

                                Object bean = endpoint.getBean();

                                ConsumerRecordType consumerRecordType = consumerRecord(method);

                                Object[] args;
                                MySQLBinlogAcknowledgment mySQLBinlogAcknowledgment = new MySQLBinlogAcknowledgment(binaryLogClient, null);

                                if (!ObjectUtils.isEmpty(consumerRecordType)) {
                                    // 序列化数据
                                    Class payloadType = consumerRecordType.getPayloadType();
                                    Object payload = serializedPayload(beanMap, payloadType);
                                    BinlogConsumerRecord binlogConsumerRecord = new BinlogConsumerRecord(table, payload);
                                    args = invokeArgs(parameterTypes, beanMap, binlogConsumerRecord, mySQLBinlogAcknowledgment);
                                } else {
                                    args = invokeArgs(parameterTypes, beanMap, mySQLBinlogAcknowledgment);
                                }
                                try {
                                    method.invoke(bean, args);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    binaryLogClient.registerEventListener(eventListener);
                }
                if (EasyMySQLBinlogListener.Pattern.INSERT.equals(pattern)) {
                    BinaryLogClient.EventListener eventListener = event -> {
                        EventData data = event.getData();
                        if (data instanceof WriteRowsEventData) {

                            WriteRowsEventData writeRowsEventData = (WriteRowsEventData) data;

                            // 获取更新字段
                            long tableId = writeRowsEventData.getTableId();
                            TableInfo table = tableAdapter.getTable(tableId);
                            String schema = endpoint.getSchema();
                            Collection<String> tables = endpoint.getTables();
                            // 正则校验数据库
                            if (ObjectUtils.isEmpty(schema) || !Pattern.matches(schema, table.getSchema())) {
                                return;
                            }
                            if (ObjectUtils.isEmpty(schema) || ObjectUtils.isEmpty(tables)
                                    || !tables.contains(table.getTableName())) {
                                return;
                            }

                            Map<Long, LazyColumn> ordinalPositionMap = table.getOrdinalPositionMap();


                            for (Serializable[] row : writeRowsEventData.getRows()) {
                                Map<String, Object> beanMap = new HashMap<>();
                                for (int i = 0; i < row.length; i++) {
                                    Serializable serializable = row[i];
                                    LazyColumn lazyColumn = ordinalPositionMap.get(i + 1L);
                                    if (null == lazyColumn) {
                                        log.warn("无法查询到表字段，请确认是否拥有表查看权限");
                                    }
                                    beanMap.put(lazyColumn.getColumnName(), serializable);
                                }

                                Method method = endpoint.getMethod();
                                Class<?>[] parameterTypes = method.getParameterTypes();

                                Object bean = endpoint.getBean();

                                ConsumerRecordType consumerRecordType = consumerRecord(method);

                                Object[] args;
                                MySQLBinlogAcknowledgment mySQLBinlogAcknowledgment = new MySQLBinlogAcknowledgment(binaryLogClient, null);

                                if (!ObjectUtils.isEmpty(consumerRecordType)) {
                                    // 序列化数据
                                    Class payloadType = consumerRecordType.getPayloadType();
                                    Object payload = serializedPayload(beanMap, payloadType);
                                    BinlogConsumerRecord binlogConsumerRecord = new BinlogConsumerRecord(table, payload);
                                    args = invokeArgs(parameterTypes, beanMap, binlogConsumerRecord, mySQLBinlogAcknowledgment);
                                } else {
                                    args = invokeArgs(parameterTypes, beanMap, mySQLBinlogAcknowledgment);
                                }
                                try {
                                    method.invoke(bean, args);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    binaryLogClient.registerEventListener(eventListener);
                }
            }


            if (!binaryLogClient.isConnected()) {
                try {
                    binaryLogClient.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("监听binlog日志失败");
                }
            }

        }

    }
}




