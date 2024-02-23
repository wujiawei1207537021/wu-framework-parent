package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.stereotype.LayerField;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 数据库字段注解  支持兼容 @JsonProperty
 *
 * @author Jia wei Wu
 * @date 2020/7/16 上午9:17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField
@LazyTableFieldId
public @interface LazyTableFieldUUId {

    @AliasFor(annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(annotation = LazyTableField.class)
    String name() default "";


    @AliasFor(annotation = LazyTableField.class, attribute = "columnType")
    String type() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "comment")
    String comment() default "";

    /**
     * 字段默认数值
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "defaultValue")
    String defaultValue() default "";

    /**
     * upsert 时候字段策略，对应生成upsert 的sql
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "upsertStrategy")
    LazyFieldStrategy upsertStrategy() default LazyFieldStrategy.NEVER_JOIN_DUPLICATE_KEY;

    /**
     * update 时候字段策略，对应生成update 的sql
     *
     * @return LazyFieldStrategy
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "updateStrategy")
    LazyFieldStrategy updateStrategy() default LazyFieldStrategy.NEVER;

    /**
     * 特权 如 select,insert,update,references
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "privileges")
    LazyTableField.Privilege[] privileges() default {LazyTableField.Privilege.select, LazyTableField.Privilege.insert, LazyTableField.Privilege.update, LazyTableField.Privilege.references};

    /**
     * id 类型 自增
     *
     * @return
     */
    LazyTableFieldId.IdType idType() default LazyTableFieldId.IdType.INPUT_ID;


}
