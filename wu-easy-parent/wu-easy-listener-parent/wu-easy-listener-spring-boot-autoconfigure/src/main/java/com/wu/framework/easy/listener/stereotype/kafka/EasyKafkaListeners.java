package com.wu.framework.easy.listener.stereotype.kafka;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyKafkaListeners {
    /**
     * Target of container annotation 'com.wu.framework.easy.listener.stereotype.kafka.EasyKafkaListeners' is not a subset of target of this annotation
     */
    EasyKafkaListener[] value();

}
