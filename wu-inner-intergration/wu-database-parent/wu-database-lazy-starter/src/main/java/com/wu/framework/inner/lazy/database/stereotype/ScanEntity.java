package com.wu.framework.inner.lazy.database.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 扫描实体路径
 * @date : 2020/8/12 下午8:35
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ScanEntity {

    @AliasFor(attribute = "path")
    String value() default "";

    @AliasFor(attribute = "value")
    String path() default "";


    String[] basePackage() default {};


}
