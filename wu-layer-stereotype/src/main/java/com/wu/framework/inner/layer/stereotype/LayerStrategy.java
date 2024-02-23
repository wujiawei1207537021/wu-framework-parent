package com.wu.framework.inner.layer.stereotype;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 层策略
 *
 * @author Jia wei Wu
 * @date 2021/4/1 下午3:32
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Component
public @interface LayerStrategy {

    /**
     * 策略 对象class
     *
     * @return
     */
    Class strategy();
}
