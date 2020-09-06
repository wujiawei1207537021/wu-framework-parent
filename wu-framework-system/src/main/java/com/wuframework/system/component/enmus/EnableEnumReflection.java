package com.wuframework.system.component.enmus;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/***
 * 枚举反射 扫描注解
 * {@link WuEnumsInit}
 * @author wjw
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface EnableEnumReflection {


//    String[] excludeName() default {};

    String[] scanBasePackages() default {};

    Class<?>[] scanBasePackageClasses() default {};
}
