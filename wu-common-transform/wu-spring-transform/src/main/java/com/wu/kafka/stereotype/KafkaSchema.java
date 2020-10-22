package com.wu.kafka.stereotype;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface KafkaSchema {

    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";
}
