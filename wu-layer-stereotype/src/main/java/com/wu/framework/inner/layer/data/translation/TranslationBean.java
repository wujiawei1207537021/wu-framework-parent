package com.wu.framework.inner.layer.data.translation;

import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * 转换对象
 *
 * @see TranslationLayer
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
@TranslationLayer
public @interface TranslationBean {


}
