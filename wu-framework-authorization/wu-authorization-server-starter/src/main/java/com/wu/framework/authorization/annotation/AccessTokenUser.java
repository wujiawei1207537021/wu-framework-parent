package com.wu.framework.authorization.annotation;

import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.web.methodresolver.AccessTokenUserMethodArgumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过令牌解析用户
 * {@link AccessTokenUserMethodArgumentResolver}
 * @see UserDetails
 */
@Target(ElementType.PARAMETER) // 可用在方法的参数上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface AccessTokenUser {
}