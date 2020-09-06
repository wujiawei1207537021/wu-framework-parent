package com.wuframework.system.component.enmus;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author wjw
 * {@link WuEnumsProperty}
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//@Import({Getter.class,AllArgsConstructor.class,NoArgsConstructor.class})
@Deprecated
public @interface WuEnumsService {

    @AliasFor(attribute = "value")
    String type() default "";

    /**
     * 枚举类型 默认类名
     * @return
     */
    @AliasFor(attribute = "type")
     String value() default "";

    /**
     *枚举类型名称
     * @return
     */
    String name() default "";

    /**
     * 是否有效
     * 0 禁用 1 启用
     * @return
     */
    int enable() default 1;

    /**
     * 是否初始化
     * @return
     */
    boolean init() default true;

}
