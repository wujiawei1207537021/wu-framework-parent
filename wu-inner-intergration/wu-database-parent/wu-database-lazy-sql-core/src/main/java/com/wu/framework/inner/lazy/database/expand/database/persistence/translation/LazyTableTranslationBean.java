package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 转译对象转译字段是个字段字段中的其他字段进行转译
 *
 * @author 吴佳伟
 * @date 2023/09/30 23:29
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LazyTableTranslationLayer
public @interface LazyTableTranslationBean {
}
