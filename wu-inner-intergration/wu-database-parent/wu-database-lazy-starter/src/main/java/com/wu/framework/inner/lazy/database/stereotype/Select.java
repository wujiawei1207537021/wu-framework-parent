package com.wu.framework.inner.lazy.database.stereotype;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 查询sql 注解
 * @date : 2020/7/31 下午10:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Select {

    String value() default "";

}
