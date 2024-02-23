package com.wu.framework.inner.lazy.database.expand.database.persistence.translation;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.aop.LazyTranslationPointcutAdvisor;

import java.lang.annotation.*;

/***
 * @see LazyTranslationPointcutAdvisor
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LazyTableArgsTranslation {
}