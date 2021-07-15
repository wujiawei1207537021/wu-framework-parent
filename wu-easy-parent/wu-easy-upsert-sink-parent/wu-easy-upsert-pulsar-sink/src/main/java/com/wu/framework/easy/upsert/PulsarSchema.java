package com.wu.framework.easy.upsert;


import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface PulsarSchema {

    String topic() default "";
}
