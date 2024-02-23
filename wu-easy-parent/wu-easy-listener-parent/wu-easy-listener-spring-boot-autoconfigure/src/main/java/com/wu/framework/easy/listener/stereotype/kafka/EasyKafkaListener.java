package com.wu.framework.easy.listener.stereotype.kafka;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyKafkaListeners.class)
@Indexed
public @interface EasyKafkaListener {


    String id() default "";


    String containerFactory() default "";

    String[] topics() default {};


    String topicPattern() default "";


    TopicPartition[] topicPartitions() default {};

    String containerGroup() default "";


    String errorHandler() default "";


    String groupId() default "";

    boolean idIsGroup() default true;


    String clientIdPrefix() default "";


    String beanRef() default "__listener";


    String concurrency() default "";


    String autoStartup() default "";


    String[] properties() default {};


    boolean splitIterables() default true;


    String contentTypeConverter() default "";
}
