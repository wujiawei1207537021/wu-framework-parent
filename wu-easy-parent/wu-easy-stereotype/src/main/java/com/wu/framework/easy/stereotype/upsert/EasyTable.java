package com.wu.framework.easy.stereotype.upsert;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface EasyTable {

    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";

    /**
     * 表注释
     *
     * @return String
     */
    String comment() default "";

    /**
     * kafka  schema 名称
     *
     * @return String
     */
    String kafkaSchemaName() default "";

    /**
     * kafka 主题 为空使用类名
     *
     * @return String
     */
    String kafkaTopicName() default "";

    /**
     * kafka code编码
     *
     * @return String
     */
    String kafkaCode() default "";

    /**
     * 数据库名 schema
     *
     * @return String
     */
    String schema() default "";

    /**
     * Elasticsearch 索引前缀
     *
     * @return String
     */
    String indexPrefix() default "";

    /**
     * Elasticsearch 索引时间格式
     *
     * @return String
     */
    String indexFormat() default "";

    /**
     * Elasticsearch 索引后缀
     */
    String indexSuffix() default "";


}
