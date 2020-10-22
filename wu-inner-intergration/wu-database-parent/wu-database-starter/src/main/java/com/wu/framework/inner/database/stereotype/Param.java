package com.wu.framework.inner.database.stereotype;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 参数注解
 * @date : 2020/7/31 下午10:25
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Param {
    String value() default "";
}
