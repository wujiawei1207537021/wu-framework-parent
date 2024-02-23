package com.wu.framework.response.mark;

import java.lang.annotation.*;

/**
 * describe : 惰性存储库操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:56
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LazyRepositoryOperation {

    /**
     * 注解描述
     *
     * @return
     */
    String value();


    /**
     * 注解标签
     *
     * @return
     */
    String tag() default "default";

}
