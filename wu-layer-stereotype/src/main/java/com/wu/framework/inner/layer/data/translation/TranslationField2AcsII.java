package com.wu.framework.inner.layer.data.translation;

import com.wu.framework.inner.layer.data.translation.api.DefaultTranslationObject2AcsIIAPI;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * 智能转换字段注解 需要对象中包含属性值为name
 *
 * @see NormalTranslation
 * @see DefaultTranslationObject2AcsIIAPI
 * @see ArgsNormalTranslation
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationField(translationAPI = DefaultTranslationObject2AcsIIAPI.class)
public @interface TranslationField2AcsII {


    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    String translationSourceName() default "";


    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    String translationTargetName() default "";

    /**
     * translationTargetType 转换目标字段类型
     */
    Class<?> translationTargetType() default Long.class;





}
