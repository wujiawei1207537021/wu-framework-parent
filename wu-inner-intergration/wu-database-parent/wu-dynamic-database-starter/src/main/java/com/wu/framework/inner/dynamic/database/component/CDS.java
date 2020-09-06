package com.wu.framework.inner.dynamic.database.component;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @describe: 切换数据库
 * @author : 吴佳伟
 * @date : 2020/8/26 下午8:05
 * @version : 1.0
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CDS {

    /**
     *
     * @return
     */
    String value();
}
