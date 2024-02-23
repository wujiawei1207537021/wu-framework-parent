package com.wu.framework.inner.layer.stereotype;


import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 层对象注入
 *
 * @param
 * @author Jia wei Wu
 * @return
 * @exception/throws
 * @date 2021/4/1 下午2:05
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LayerInject {
}
