package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 转译对象
 *
 * @author 吴佳伟
 * @date 2023/09/30 23:29
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LazyTableTranslationLayer {
}
