package com.wu.framework.inner.redis.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @ Description   :  redis 清除key 注解
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/11/14 0014 11:25
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/11/14 0014 11:25
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface RedisClear {
    /**
     * 前缀
     *
     * @return
     */
    String prefix() default "";

    /**
     * 后缀
     *
     * @return
     */
    String suffix() default "";

    /**
     * 键值
     *
     * @return
     */
    String key() default "";

    /**
     * 键值别名
     *
     * @return
     */
    String nameAlias() default "";

    /**
     * 键值存在的对象
     *
     * @return
     */
    Class nameClass() default String.class;
}


