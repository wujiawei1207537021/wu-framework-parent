package com.wu.framework.authorization.annotation;

import com.wu.framework.authorization.web.aop.AccessLimitAOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问限制
 * {@link AccessLimitAOP}
 *
 * @author wjw
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    int seconds();

    int maxCount();

    boolean needLogin() default true;

    /**
     * 默认请求成功限制次数
     *
     * @return
     */
    boolean requestSuccessLimit() default true;

    boolean checkAccessParam() default false;

    Class paramType() default String.class;

    String paramName() default "";
}
