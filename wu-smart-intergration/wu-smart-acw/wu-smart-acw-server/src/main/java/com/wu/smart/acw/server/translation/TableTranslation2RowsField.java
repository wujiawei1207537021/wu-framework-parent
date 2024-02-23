package com.wu.smart.acw.server.translation;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.smart.acw.server.translation.api.TableTranslationRowsAPI;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 通过表信息转译表行信息
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationField(translationAPI = TableTranslationRowsAPI.class)
public @interface TableTranslation2RowsField {
    /**
     * translationSourceName  当前对象中字段名（根据当前字段value从 中转数据中获取）
     */
    @AliasFor(annotation = TranslationField.class)
    String translationSourceName() default "";

}
