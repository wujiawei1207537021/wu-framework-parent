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
@LazyTableField(indexType = LayerField.LayerFieldType.ID)
public @interface LazyTableFieldId {

    @AliasFor(annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(annotation = LazyTableField.class)
    String name() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "columnType")
    String type() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "comment")
    String comment() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "indexType")
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.ID;

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
