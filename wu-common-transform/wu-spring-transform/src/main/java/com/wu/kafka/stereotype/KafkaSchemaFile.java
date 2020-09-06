package com.wu.kafka.stereotype;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface KafkaSchemaFile {

    String name() default "";

    String field() ;

    int version() default 1;

    int scale() default 0;

    String parameters() default "" ;

    String type() default "string";

    boolean optional() default true;
}
