package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.aop.LazyTranslationPointcutAdvisor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 适配于手动转换ORM存储对象
 *
 * @author 吴佳伟
 * @date 2023/09/30 23:29
 * @see LazyTranslationPointcutAdvisor
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LazyTableTranslation {
}
