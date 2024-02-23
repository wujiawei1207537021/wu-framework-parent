package com.wu.framework.easy.listener.stereotype.pulsar;

import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyPulsarListeners.class)
@Indexed
public @interface EasyPulsarListener {

    String id() default "";

    /**
     * 主题
     *
     * @return
     */
    String[] topics() default {};

    /**
     * 订阅名称
     *
     * @return
     */
    String subscriptionName() default "";

    /**
     * 订阅类型共享形
     */
    SubscriptionType subscriptionType() default SubscriptionType.Shared;

    /**
     * 消费线程数量
     */
    String concurrency() default "1";

}
