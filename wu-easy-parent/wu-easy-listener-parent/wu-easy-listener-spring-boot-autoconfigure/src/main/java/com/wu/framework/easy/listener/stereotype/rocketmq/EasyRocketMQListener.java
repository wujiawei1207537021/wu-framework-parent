package com.wu.framework.easy.listener.stereotype.rocketmq;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyRocketMQListeners.class)
@Indexed
public @interface EasyRocketMQListener {

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

    /**
     * 消息模式
     *
     * @return
     */
    MessageModel messageModel() default MessageModel.BROADCASTING;

    /**
     * 批量获取数据条数
     *
     * @return
     */
    int consumeMessageBatchMaxSize() default 1;

    /**
     * 从哪里消费
     */
    ConsumeFromWhere consumeFromWhere() default ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;


}
