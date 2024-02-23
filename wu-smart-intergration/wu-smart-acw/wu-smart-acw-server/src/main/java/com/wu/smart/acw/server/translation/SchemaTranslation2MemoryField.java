package com.wu.smart.acw.server.translation;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.smart.acw.server.translation.api.SchemaTranslationMemoryAPI;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 通过数据库信息转译表内存数据
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationField(translationAPI = SchemaTranslationMemoryAPI.class)
public @interface SchemaTranslation2MemoryField {
    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    @AliasFor(annotation = TranslationField.class)
    String translationSourceName() default "";

}
