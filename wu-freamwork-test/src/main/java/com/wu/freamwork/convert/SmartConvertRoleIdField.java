package com.wu.freamwork.convert;


import com.wu.framework.inner.layer.data.translation.TranslationField;
import org.springframework.core.annotation.AliasFor;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/09/22 12:48
 */
@TranslationField(translationAPI = RoleIdConvert2RoleAdapter.class)
public @interface SmartConvertRoleIdField {

    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    @AliasFor(annotation = TranslationField.class)
    String translationSourceName() default "";

    /**
     * transferDataName 中转数据名称（中转数据名称，从此数据中获取对象然后赋值给当前字段）
     */
    @AliasFor(annotation = TranslationField.class)
    String transferDataName() default "";

    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    @AliasFor(annotation = TranslationField.class)
    String translationTargetName() default "";

    /**
     * translationTargetType 转换目标字段类型
     */
    @AliasFor(annotation = TranslationField.class)
    Class<?> translationTargetType() default String.class;
}
