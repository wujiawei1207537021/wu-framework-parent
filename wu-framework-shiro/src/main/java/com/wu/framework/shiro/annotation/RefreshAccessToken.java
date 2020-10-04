package com.wu.framework.shiro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 刷新令牌内数据信息
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RefreshAccessToken {

    String refreshType() default  "user_name";

    Class paramType() default String.class;

    String paramName() default "";
}
