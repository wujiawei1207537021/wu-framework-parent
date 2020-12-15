package com.wu.framework.easy.stereotype.upsert;

import java.lang.annotation.*;

/**
 * description SmartMark 智能标记
 * {@link EasySmart}
 *
 * @author Jia wei Wu
 * @date 2020/12/14 下午12:07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmartMark {

    /**
     * 深度标记 true 继续下钻 下钻标记
     */
    boolean drillDown() default false;

}
