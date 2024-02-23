package com.wu.framework.easy.listener.stereotype;

import com.wu.framework.easy.listener.DynamicListenerType;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 动态监听 适配多种组件数据接收
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/15 7:49 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@Deprecated
public @interface DynamicListener {


    /**
     * describe 使用的策略
     *
     * @author Jia wei Wu
     * @date 2021/10/15 7:57 下午
     **/
    DynamicListenerType strategy();


}
