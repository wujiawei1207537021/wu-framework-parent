package com.wuframework.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 通过令牌解析用户
 * {@link com.wuframework.shiro.web.methodresolver.AccessTokenUserMethodArgumentResolver}
 */
@Target(ElementType.PARAMETER) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface AccessTokenUser { }
