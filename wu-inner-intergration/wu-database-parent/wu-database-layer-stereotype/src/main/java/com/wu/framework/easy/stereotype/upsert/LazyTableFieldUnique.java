package com.wu.framework.easy.stereotype.upsert;

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
@LazyTableField(indexType = LayerField.LayerFieldType.UNIQUE)
public @interface LazyTableFieldUnique {

    @AliasFor(annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(annotation = LazyTableField.class)
    String name() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "type")
    String type() default "";

    @AliasFor(annotation = LazyTableField.class, attribute = "comment")
    String comment() default "";
}
