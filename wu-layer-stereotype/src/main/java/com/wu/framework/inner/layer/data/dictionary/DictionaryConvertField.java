package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * 转换字段注解 需要对象中包含属性值为name
 *
 * @see TranslationField
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
public @interface DictionaryConvertField {

    /**
     * 字典项转换中文名称后的属性 默认是字典项+Name
     */
    String chineseNameProperty() default "";

    /**
     * 字典
     */
    String convertItem() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";


}
