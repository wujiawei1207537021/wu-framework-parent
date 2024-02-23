package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTableTranslationOneAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
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
@LazyTableTranslationLayer
public @interface LazyTableTranslationOneField {

    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    String translationSourceName() default "";

    /**
     * transferDataName 中转数据名称（中转数据名称，从此数据中获取对象然后赋值给当前字段）
     */
    String transferDataName() default "";

    /**
     * 转换默认目标表
     *
     * @return 目标表
     */
    String translationTargetTableName() default "";

    /**
     * 转换目标表class 如果字符串存在以字符串为准
     */
    Class<?> translationTargetTableClass() default Void.class;


    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    String translationTargetName() default "";

    /**
     * translationTargetType 转换目标字段类型
     */
    Class<?> translationTargetType() default String.class;


    /**
     * 需要映射的字段 默认转换成下划线
     */
    String[] columnList() default {};

    /**
     * 查询类型 字段、ALL
     */
    LazyTranslationTableEndpoint.Type type() default LazyTranslationTableEndpoint.Type.ALL;

    /**
     * 映射使用的API
     */
    Class<? extends LazyTranslationAPI> lazyTranslationAPIClass() default LazyTableTranslationOneAPI.class;
}
