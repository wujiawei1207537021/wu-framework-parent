package com.wu.framework.shiro.annotation;

import com.wu.framework.shiro.web.interceptors.RemoveAccessTokenInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 移出令牌据信息
 * <p>
 * {@link RemoveAccessTokenInterceptor}
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoveAccessToken {

    boolean always() default false;

    boolean success() default true;
}
