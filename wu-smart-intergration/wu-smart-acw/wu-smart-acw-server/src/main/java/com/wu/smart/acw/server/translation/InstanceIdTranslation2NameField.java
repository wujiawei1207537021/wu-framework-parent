package com.wu.smart.acw.server.translation;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.smart.acw.server.translation.api.InstanceIdTranslation2NameAPI;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 智能转换字段注解 需要对象中包含属性值为name
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationField(translationAPI = InstanceIdTranslation2NameAPI.class)
public @interface InstanceIdTranslation2NameField {
    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    @AliasFor(annotation = TranslationField.class)
    String translationSourceName() default "";

}
