package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.data.NormalUsedString;
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
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField
@LazyTableField(lazyTableIndexs = {@LazyTableIndex(indexType = LayerField.LayerFieldType.ID)}, key = true, notNull = true)
public @interface LazyTableFieldId {

    @AliasFor(annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(annotation = LazyTableField.class)
    String name() default "";

    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     */
    @AliasFor(annotation = LazyTableField.class)
    String extra() default NormalUsedString.AUTO_INCREMENT;

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
     * <p>
     * 默认upsert 操作不更改主键
     * </p>
     *
     * @return LazyFieldStrategy
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
    IdType idType() default IdType.AUTOMATIC_ID;

    enum IdType {
        //  主键ID 默认自增
        AUTOMATIC_ID,
        // 输入主键
        INPUT_ID,

    }

}
