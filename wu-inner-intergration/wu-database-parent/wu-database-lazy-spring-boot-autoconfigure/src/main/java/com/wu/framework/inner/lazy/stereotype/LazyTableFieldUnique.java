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
@LazyTableField(lazyTableIndexs = {@LazyTableIndex(indexType = LayerField.LayerFieldType.UNIQUE)})
public @interface LazyTableFieldUnique {

    @AliasFor(annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(annotation = LazyTableField.class)
    String name() default "";

    /**
     * 字段别名
     *
     * @see LambdaBasicComparison#as()
     */
    @Deprecated
    @AliasFor(annotation = LazyTableField.class, attribute = "alias")
    String alias() default "";

    /**
     * 数据库字段类型
     *
     * @see LazyTableFieldUnique#columnType()
     */
    @Deprecated
    @AliasFor(annotation = LazyTableField.class, attribute = "columnType")
    String type() default "";


    /**
     * 数据库字段类型
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "columnType")
    String columnType() default "";

    /**
     * 字段描述
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "comment")
    String comment() default "";

    /**
     * 不是空
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "notNull")
    boolean notNull() default false;

    /**
     * 字段默认数值
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "defaultValue")
    String defaultValue() default "";

    /**
     * 是否为主键
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "key")
    boolean key() default false;

    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "extra")
    String extra() default "";

    /**
     * 特权 如 select,insert,update,references
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "privileges")
    LazyTableField.Privilege[] privileges() default {LazyTableField.Privilege.select, LazyTableField.Privilege.insert, LazyTableField.Privilege.update, LazyTableField.Privilege.references};

    /**
     * upsert 时候字段策略，对应生成upsert 的sql
     *
     * @return
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "upsertStrategy")
    LazyFieldStrategy upsertStrategy() default LazyFieldStrategy.NO_VERIFY;

    /**
     * update 时候字段策略，对应生成update 的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy updateStrategy() default LazyFieldStrategy.IGNORED_NULL;
}
