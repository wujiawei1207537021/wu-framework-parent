package com.wuframework.system.component.enmus;

import org.springframework.stereotype.Component;

/***
 * 枚举反射 扫描注解
 * {@link WuEnumsInit}
 * 即将弃用 请参考使用 EnableEnumReflection
 * {@link EnableEnumReflection}
 */

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
@Deprecated
public @interface WuEnumReflectionService {


//    String[] excludeName() default {};

    String[] scanBasePackages() default {};

    Class<?>[] scanBasePackageClasses() default {};
}
