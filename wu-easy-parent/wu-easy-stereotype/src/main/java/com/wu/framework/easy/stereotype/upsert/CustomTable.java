package com.wu.framework.easy.stereotype.upsert;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CustomTable {

    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";

    String comment() default "";

    /**
     * kafka  schema 名称
     * @return
     */
    String kafkaSchemaName() default "";

    /**
     * kafka 主题 为空使用类名
     * @return
     */
    String kafkaTopicName() default "";

    /**
     * kafka code编码
     * @return
     */
    String kafkaCode() default "";

}
