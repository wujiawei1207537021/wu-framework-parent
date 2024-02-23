package com.wu.framework.inner.lazy.stereotype;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LazyTransactional {

    /**
     * 回滚异常
     *
     * @return
     */
    Class<? extends Throwable>[] rollbackFor() default {};
}
