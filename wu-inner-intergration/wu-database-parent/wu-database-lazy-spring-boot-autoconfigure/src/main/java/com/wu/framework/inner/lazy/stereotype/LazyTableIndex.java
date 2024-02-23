package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.stereotype.LayerField;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 数据库表索引
 *
 * @author wujiawei
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField
public @interface LazyTableIndex {

    /**
     * 字段索引类型(数据库)
     */
    @AliasFor(attribute = "indexType", annotation = LayerField.class)
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.NONE;

    /**
     * 索引名称 当索引类型不是NONE 时有效 默认按照字段 a_b 形成indexName
     */
    @AliasFor(attribute = "indexName", annotation = LayerField.class)
    String indexName() default "";

}
