package com.wu.framework.easy.listener.stereotype.redis;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
@Repeatable(EasyRedisListeners.class)
@Indexed
public @interface EasyRedisListener {

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


    int database() default -1;
}
