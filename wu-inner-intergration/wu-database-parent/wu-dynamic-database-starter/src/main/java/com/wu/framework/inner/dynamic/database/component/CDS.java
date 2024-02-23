package com.wu.framework.inner.dynamic.database.component;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 切换数据库
 * @date : 2020/8/26 下午8:05
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CDS {

    /**
     * @return
     */
    String value();
}
