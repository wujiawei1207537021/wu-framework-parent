package com.wu.framework.easy.listener.stereotype;


import com.wu.framework.easy.listener.DynamicListenerType;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;


/**
 * 生名在EasyListenerAnnotationBeanPostProcessor 的实现类上
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/16 7:44 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ListenerProcessorStrategy {

    /**
     * 策略 对象class
     *
     * @return
     */
    DynamicListenerType strategy();
}
