package com.wu.framework.easy.listener;


/**
 * 动态监听类型
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/15 7:55 下午
 */
public enum DynamicListenerType {
    /**
     * 默认监听所有
     */
    ALL,
    /**
     * kafka 数据监听
     */
    KAFKA,
    /**
     * mysql 数据监听
     */
    MYSQL,
    /**
     * mysql binlog 数据监听
     */
    BINLOG,
    /**
     * pulsar 数据监听
     */
    PULSAR,
    /**
     * redis 数据监听
     */
    REDIS,
    /**
     * Rocket 数据监听
     */
    ROCKETMQ,
    /**
     * rabbit 数据监听
     */
    RABBITMQ,
    /**
     * active 数据监听
     */
    ACTIVEMQ
}
