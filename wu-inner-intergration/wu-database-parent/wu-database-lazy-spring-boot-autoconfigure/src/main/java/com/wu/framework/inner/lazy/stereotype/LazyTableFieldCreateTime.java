package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.data.DefaultIEnum;
import com.wu.framework.inner.layer.data.IEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 数据库字段注解  支持兼容 @JsonProperty
 *
 * @author Jia wei Wu
 * @date 2020/7/16 上午9:17
 * @see com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LazyTableField(defaultValue = "CURRENT_TIMESTAMP")
public @interface LazyTableFieldCreateTime {

    /**
     * 字段名
     */
    @AliasFor(attribute = "name", annotation = LazyTableField.class)
    String value() default "";

    @AliasFor(attribute = "value", annotation = LazyTableField.class)
    String name() default "";

    /**
     * 字段别名
     */
    String alias() default "";

    /**
     * 数据库字段类型
     */
    @AliasFor(attribute = "columnType", annotation = LazyTableField.class)
    String columnType() default "datetime";

    /**
     * 字段描述
     */
    String comment() default "创建时间";

    /**
     * 不是空
     *
     * @return
     */
    boolean notNull() default false;

    /**
     * 是否为主键
     *
     * @return
     */
    boolean key() default false;


    /**
     * 特权 如 select,insert,update,references
     *
     * @return
     */
    LazyTableField.Privilege[] privileges() default {LazyTableField.Privilege.select, LazyTableField.Privilege.insert, LazyTableField.Privilege.update, LazyTableField.Privilege.references};

    /**
     * 是否存在
     */
    boolean exist() default true;

    /**
     * upsert 时候字段策略，对应生成upsert 的sql
     *
     * @return
     */
    LazyFieldStrategy upsertStrategy() default LazyFieldStrategy.IGNORED_NULL;

    /**
     * update 时候字段策略，对应生成update 的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy updateStrategy() default LazyFieldStrategy.IGNORED_NULL;


    /**
     * 字段版本
     */
    int version() default 1;

    /**
     * 字段规模 对应的column_scale
     *
     * @return
     */
    long scale() default 0L;

    /**
     * 参数
     */
    String parameters() default "";

    /**
     * 可选的
     */
    boolean optional() default true;


    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    Class<? extends IEnum> iEnum() default DefaultIEnum.class;

    /**
     * 数据格式
     */
    String dataType() default "";


    /**
     * 通过获取字典类型转换
     *
     * @return
     */
    String convert() default "";


//    /**
//     * 字段索引类型(数据库)
//     */
//    @AliasFor(attribute = "indexType", annotation = LayerField.class)
//    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.NONE;
//
//    /**
//     * 索引名称 当索引类型不是NONE 时有效
//     */
//    @AliasFor(attribute = "indexName", annotation = LayerField.class)
//    String indexName() default "";

    /**
     * 数据库索引
     *
     * @return
     */
    LazyTableIndex[] lazyTableIndexs() default {@LazyTableIndex};


}
