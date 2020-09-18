package com.wu.framework.inner.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法参数中使用此注解
 * 下游实现
 * {@link com.wu.framework.easy.stereotype.web.RequestPage}
 */
@Target(ElementType.PARAMETER) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Deprecated
public @interface RequestPage { }
