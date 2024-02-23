package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTableTranslationOneToManyAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 转译字段
 *
 * @author 吴佳伟
 * @date 2023/09/30 23:29
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LazyTableTranslationOneField
public @interface LazyTableTranslationOneToManyField {

    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    @AliasFor(attribute = "translationSourceName", annotation = LazyTableTranslationOneField.class)
    String translationSourceName() default "";


    /**
     * 转换默认目标表
     *
     * @return 目标表
     */
    @AliasFor(attribute = "translationTargetTableName", annotation = LazyTableTranslationOneField.class)
    String translationTargetTableName() default "";

    /**
     * 转换目标表class 如果字符串存在以字符串为准
     */
    @AliasFor(attribute = "translationTargetTableClass", annotation = LazyTableTranslationOneField.class)
    Class<?> translationTargetTableClass() default Void.class;

    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    @AliasFor(attribute = "translationTargetName", annotation = LazyTableTranslationOneField.class)
    String translationTargetName() default "";

    /**
     * translationTargetType 转换目标字段类型
     */
    @AliasFor(attribute = "translationTargetType", annotation = LazyTableTranslationOneField.class)
    Class<?> translationTargetType() default Object.class;


    /**
     * 需要映射的字段 默认转换成下划线
     */
    @AliasFor(attribute = "columnList", annotation = LazyTableTranslationOneField.class)
    String[] columnList() default {};

    /**
     * 查询类型 字段、ALL
     */
    @AliasFor(attribute = "type", annotation = LazyTableTranslationOneField.class)
    LazyTranslationTableEndpoint.Type type() default LazyTranslationTableEndpoint.Type.ALL;

    /**
     * 映射使用的API
     */
    @AliasFor(attribute = "lazyTranslationAPIClass", annotation = LazyTableTranslationOneField.class)
    Class<? extends LazyTranslationAPI> lazyTranslationAPIClass() default LazyTableTranslationOneToManyAPI.class;
}
