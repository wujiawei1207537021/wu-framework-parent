package com.wu.framework.inner.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 开启sql优化
 * {@link com.wu.framework.easy.stereotype.web.EnableSQLOptimization}
 */
@Target(ElementType.PARAMETER) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Deprecated
public @interface EnableSQLOptimization { }
