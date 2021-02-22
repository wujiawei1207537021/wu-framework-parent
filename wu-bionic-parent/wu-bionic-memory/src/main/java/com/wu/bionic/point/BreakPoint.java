package com.wu.bionic.point;


import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;


/**
 * description  断点注解
 * @author 吴佳伟
 * @date 2021/2/4 上午10:15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface BreakPoint {

    // 事件名称
    String eventName() default "";

}
