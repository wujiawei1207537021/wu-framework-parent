package com.wu.framework.inner.lazy.stereotype;

import java.lang.annotation.*;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 20:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface LazyScans {

    LazyScan[] value();
}
