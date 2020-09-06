package com.wuframework.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 移出令牌据信息
 *
 *  {@link com.wuframework.shiro.web.interceptors.RemoveAccessTokenInterceptor}
 *
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoveAccessToken {

    boolean always() default false;

    boolean success() default true;
}
