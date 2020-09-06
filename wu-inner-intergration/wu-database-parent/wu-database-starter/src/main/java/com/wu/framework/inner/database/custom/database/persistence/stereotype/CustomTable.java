package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CustomTable {
    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";

    /**
     * 表注释
     * @return
     */
    String comment() default "";

    /**
     * 数据库名 schema
     * @return
     */
    String schema() default "";

}
