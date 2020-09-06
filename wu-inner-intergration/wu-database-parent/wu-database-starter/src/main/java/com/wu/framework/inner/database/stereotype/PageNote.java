package com.wu.framework.inner.database.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @describe: 分页注解  指定位置添加分页
 * @author : 吴佳伟
 * @date : 2020/8/2 下午6:11
 * @version : 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface PageNote {

    @AliasFor(attribute = "name")
    String value() default "";

    /**
     * sql语句中的别名  select * from user where id in (select * from person) AS xx
     * @return
     */
    @AliasFor(attribute = "value")
    String name() default "";

}
