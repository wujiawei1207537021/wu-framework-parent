package com.wu.framework.inner.layer.stereotype;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 层字段注解
 *
 * @param
 * @author Jia wei Wu
 * @return
 * @exception/throws
 * @date 2021/4/1 下午2:05
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LayerField {

    boolean exist() default true;

    /**
     * 字段名(默认驼峰)
     */
    @AliasFor(attribute = "value")
    String name() default "";

    @AliasFor(attribute = "name")
    String value() default "";

    /**
     * 字段索引类型
     *
     * @return
     */
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.NONE;

    /**
     * 索引名称 当索引类型不是NONE 时有效
     */
    String indexName() default "";

    enum LayerFieldType {
        // 字段类型
        NONE,
        // 主键自增索引
        ID,
        // 唯一性索引
        UNIQUE,
        // 全文 索引 only apply varchar
        FULLTEXT,
        // 正常索引
        NORMAL,
        // 空间索引 创建空间索引的列必须geometry指明not null, mysql中   只有MyiSAM存储引擎支持空间索引
        SPATIAL,
        // 自动的
        AUTOMATIC;
    }
}
