package com.wu.framework.easy.listener.stereotype.kafka;

import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
public @interface KafkaHandler {

    /**
     * When true, designate that this is the default fallback method if the payload columnType
     * matches no other {@link KafkaHandler} method. Only one method can be so designated.
     *
     * @return true if this is the default method.
     * @since 2.1.3
     */
    boolean isDefault() default false;

}
