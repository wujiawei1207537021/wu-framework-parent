package com.wu.framework.easy.mysql.listener;

import com.wu.framework.easy.listener.core.ListenerConsumer;
import com.wu.framework.easy.listener.core.SingletonMessageListenerContainer;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.consumer.ConsumerRecords;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.mysql.listener.config.GeneralLog;
import com.wu.framework.easy.mysql.listener.config.ListenerConsumerLog;
import com.wu.framework.easy.mysql.listener.config.MethodMySQLListenerEndpoint;
import com.wu.framework.easy.mysql.listener.consumer.MySQLConsumerRecord;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class MySQLSingletonMessageListenerContainer<K, V> implements SingletonMessageListenerContainer<K, V> {

    private final Logger log = LoggerFactory.getLogger(MySQLSingletonMessageListenerContainer.class);
    protected ListenerConsumer listenerConsumer;
    protected boolean running;
    private String statement;
    private String beanName;

    private LazySqlOperation operation;

    private MethodMySQLListenerEndpoint endpoint;


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

        switch (endpoint.getPattern()) {
            case STATEMENT:
                this.listenerConsumer = new MySQLListenerStatementConsumer();
                break;
            case GENERAL_LOG:
                this.listenerConsumer = new MySQLListenerConsumer();
                break;
            default:
                return;
        }

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

    public void setOperation(LazySqlOperation operation) {
        this.operation = operation;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setEndpoint(MethodMySQLListenerEndpoint endpoint) {
        this.endpoint = endpoint;
    }


    final class MySQLListenerStatementConsumer implements ListenerConsumer {


        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {
            // 开始偏移量
            Long startOffset = null;
            // 结束偏移量
            Long endOffset = null;

            final Method method = endpoint.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();

            Object bean = endpoint.getBean();


            while (isRunning()) {
                endOffset = System.currentTimeMillis() / 1000;

                List<EasyHashMap> records = null;
                try {
                    records = operation.executeSQL(statement, EasyHashMap.class);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                // TODO 参数类型问题
                if (!ObjectUtils.isEmpty(records)) {
                    // 判断第一个参数是什么
                    if (ConsumerRecords.class.isAssignableFrom(parameterTypes[0])) {
                        try {
                            List<ConsumerRecord> consumerRecordList = new ArrayList<>();
                            for (EasyHashMap record : records) {
                                final ConsumerRecord consumerRecord = new MySQLConsumerRecord(null, record);
                                consumerRecordList.add(consumerRecord);
                            }
                            method.invoke(bean, (ConsumerRecords) consumerRecordList::iterator);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (ConsumerRecord.class.isAssignableFrom(parameterTypes[0])) {
                        if (method.getParameterCount() == 1) {
                            try {
                                method.invoke(bean, new MySQLConsumerRecord(null, records));
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                method.invoke(bean, new MySQLConsumerRecord(null, records), (Acknowledgment) () -> {
                                });
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            method.invoke(bean, records);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    // 存储 偏移量
                    operation.upsert(
                            new ListenerConsumerLog().
                                    setDatabaseName("mysql").setTableName("general_log").
                                    setConsumer(endpoint.getId()).
                                    setOffSet(endOffset));


                }
                // 休息一会儿
                try {
                    Thread.sleep(endpoint.getSleep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startOffset = endOffset;
            }


        }

    }

    /**
     * 开启日志模式(推荐)
     * <p>
     * -- 1、设置
     * <p>
     * -- SET GLOBAL log_outand = 'TABLE';SET GLOBAL general_log = 'ON';  //日志开启
     * <p>
     * -- SET GLOBAL log_outand = 'TABLE'; SET GLOBAL general_log = 'OFF';  //日志关闭
     * <p>
     * -- 2、查询
     * <p>
     * SELECT * from mysql.general_log ORDER BY event_time DESC;
     * <p>
     * -- 3、清空表（delete对于这个表，不允许使用，只能用truncate）
     * <p>
     * -- truncate table mysql.general_log;
     */
    final class MySQLListenerConsumer implements ListenerConsumer {


        @Override
        public boolean isLongLived() {
            return true;
        }

        @Override
        public void run() {
            // 开始偏移量
            Long startOffset = null;
            // 结束偏移量
            Long endOffset;

            final Method method = endpoint.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();

            Object bean = endpoint.getBean();


            String sqlTemple = "SELECT * from mysql.general_log %s ORDER BY event_time DESC;";
            while (isRunning()) {
                String statement;
                endOffset = System.currentTimeMillis() / 1000;
                if (null == startOffset) {
                    statement = String.format(sqlTemple, " where UNIX_TIMESTAMP(event_time) < " + endOffset);
                } else {
                    statement = String.format(sqlTemple, " where UNIX_TIMESTAMP(event_time) >= " + startOffset + " and UNIX_TIMESTAMP(event_time) < " + endOffset);
                }

                List<GeneralLog> records = null;
                try {
                    records = operation.executeSQL(statement, GeneralLog.class);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                // TODO 参数类型问题
                if (!ObjectUtils.isEmpty(records)) {
                    // 判断第一个参数是什么
                    if (ConsumerRecords.class.isAssignableFrom(parameterTypes[0])) {
                        try {
                            List<ConsumerRecord> consumerRecordList = new ArrayList<>();
                            for (GeneralLog record : records) {
                                final ConsumerRecord consumerRecord = new MySQLConsumerRecord(null, record);
                                consumerRecordList.add(consumerRecord);
                            }
                            method.invoke(bean, (ConsumerRecords) consumerRecordList::iterator);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else if (ConsumerRecord.class.isAssignableFrom(parameterTypes[0])) {
                        if (method.getParameterCount() == 1) {
                            try {
                                method.invoke(bean, new MySQLConsumerRecord(null, records));
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                method.invoke(bean, new MySQLConsumerRecord(null, records), (Acknowledgment) () -> {
                                });
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            method.invoke(bean, records);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    // 存储 偏移量
                    operation.upsert(
                            new ListenerConsumerLog().
                                    setDatabaseName("mysql").setTableName("general_log").
                                    setConsumer(endpoint.getId()).
                                    setOffSet(endOffset));


                }
                // 休息一会儿
                try {
                    Thread.sleep(endpoint.getSleep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startOffset = endOffset;
            }


        }

    }
}




