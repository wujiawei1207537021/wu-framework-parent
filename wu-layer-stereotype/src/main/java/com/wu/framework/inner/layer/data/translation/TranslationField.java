package com.wu.framework.inner.layer.data.translation;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * 智能转换字段注解 需要对象中包含属性值为name
 *
 * @see NormalTranslation
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationLayer
public @interface TranslationField {

//    /**
//     * 字典项类型 树状 或列表
//     *
//     * @return
//     */
//    ConvertEntryType type() default ConvertEntryType.LIST;

//    /**
//     * 字典项转换中文名称后的属性 默认是字典项+Name
//     */
//    String chineseNameProperty() default "";


    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    String translationSourceName() default "";

    /**
     * transferDataName 中转数据名称（中转数据名称，从此数据中获取对象然后赋值给当前字段）
     */
    String transferDataName() default "";

    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    String translationTargetName() default "";

    /**
     * translationTargetType 转换目标字段类型
     */
    Class<?> translationTargetType() default String.class;

    /**
     * 默认值
     */
    String defaultValue() default "";

    /**
     * 转换适配器
     */
    Class<? extends TranslationAPI> translationAPI();

    /**
     * 分割字符
     *
     * @return
     */
    String[] convertSplitCharacter() default ",";


    /**
     * 字典项类型
     */
    enum ConvertEntryType {
        /**
         * 树状
         */
        TREE,
        /**
         * 列表
         */
        LIST;

    }
}
