package com.wu.framework.easy.listener.stereotype;


import com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListener;
import com.wu.framework.easy.listener.stereotype.mysql.EasyMySQLListener;
import com.wu.framework.easy.listener.stereotype.mysql.binlog.EasyMySQLBinlogListener;
import com.wu.framework.easy.listener.stereotype.pulsar.EasyPulsarListener;
import com.wu.framework.easy.listener.stereotype.redis.EasyRedisListener;
import com.wu.framework.easy.listener.stereotype.rocketmq.EasyRocketMQListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 最简单的监听
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/15 8:03 下午
 * @see ConsumerRecord
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MessageMapping
@Indexed
public @interface EasyListener {


    /**
     * Kafka 监听器
     *
     * @return
     */
    EasyKafkaListener kafkaListener() default @EasyKafkaListener();

    /**
     * 数据库监听器
     *
     * @return
     */
    EasyMySQLListener mySQLListener() default @EasyMySQLListener();

    /**
     * 数据库binlog日志监听
     */
    EasyMySQLBinlogListener binlogListener() default @EasyMySQLBinlogListener();

    /**
     * pulsarListener 监听器
     *
     * @return
     */
    EasyPulsarListener pulsarListener() default @EasyPulsarListener();

    /**
     * rocketmq  监听器
     *
     * @return
     */
    EasyRocketMQListener rocketMqListener() default @EasyRocketMQListener;

    /**
     * redis 监听器
     */
    EasyRedisListener redisListener() default @EasyRedisListener();


    /**
     * 主题
     *
     * @return
     */
    String[] topics() default {};

    /**
     * 线程数量
     *
     * @return
     */
    String concurrency() default "1";

    /**
     * 消费者
     *
     * @return
     */
    String consumer() default "#default";

}
