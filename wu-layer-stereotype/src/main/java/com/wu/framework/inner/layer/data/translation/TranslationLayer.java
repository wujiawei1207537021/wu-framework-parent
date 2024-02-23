package com.wu.framework.inner.layer.data.translation;

import com.wu.framework.inner.layer.stereotype.LayerField;

import java.lang.annotation.*;

/**
 * description 转译注解 被TranslationField、TranslationBean 实现
 *
 * @author 吴佳伟
 * @date 2023/09/22 13:27
 * @see TranslationBean
 * @see TranslationField
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
public @interface TranslationLayer {
}
