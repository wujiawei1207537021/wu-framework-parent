package com.wu.framework.inner.redis.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @ Description   :  reids切换数据库 注解
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/11/14 0014 11:06
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/11/14 0014 11:06
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface LazyRedis {
    /**
     * 默认redis数据库
     *
     * @return
     */
    int database() default 0;
}


