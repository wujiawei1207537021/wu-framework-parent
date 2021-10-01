package com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : 吴佳伟
 * @version 1.0
 * describe :
 * @date : 2021/7/4 6:00 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LazyDS {
    /**
     * 数据源名称(MYSQL多数据源有效)
     *
     * @return
     */
    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";

}
