package com.wu.framework.inner.swagger.annotation;

import com.wu.framework.easy.stereotype.web.EasyController;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 自定义组合注解  CustomController
 * 包含 @RestController @RequestMapping 待完善
 * {@link EasyController}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
@Inherited
@Deprecated
public @interface CustomController {


    /**
     * Alias for {@link RequestMapping#name}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    /**
     * Alias for {@link RequestMapping#value}.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};

    /**
     * Alias for {@link RequestMapping#path}.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] path() default {};


}
