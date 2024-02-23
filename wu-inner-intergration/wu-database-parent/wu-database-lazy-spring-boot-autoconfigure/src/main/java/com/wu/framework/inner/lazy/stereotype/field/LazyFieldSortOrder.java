package com.wu.framework.inner.lazy.stereotype.field;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 字段排序规则
 */

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LazyFieldSortOrder {
    /**
     * 排序规则
     */
    SortOrder sortOrder() default SortOrder.NONE;


    /**
     * 排序规则
     */
    public static enum SortOrder {
        DESC,
        ASC,
        NONE
    }
}
