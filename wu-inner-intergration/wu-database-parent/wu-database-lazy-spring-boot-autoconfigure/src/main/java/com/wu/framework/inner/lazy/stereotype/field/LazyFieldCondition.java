package com.wu.framework.inner.lazy.stereotype.field;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * describe : 字段查询条件
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/31 00:01
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LazyFieldCondition {
    /**
     * 条件
     *
     * @return
     */
    String condition() default "";

    /**
     * 是否忽视空
     *
     * @return
     */
    boolean ignoreEmpty() default true;

    /**
     * 是否忽视
     */
    boolean ignore() default false;
}
