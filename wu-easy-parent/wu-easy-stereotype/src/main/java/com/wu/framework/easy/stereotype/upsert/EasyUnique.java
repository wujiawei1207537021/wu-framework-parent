package com.wu.framework.easy.stereotype.upsert;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/7/14 下午6:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasySmartField(indexType = EasySmartField.TableFileIndexType.UNIQUE)
public @interface EasyUnique {

    @AliasFor(annotation = EasySmartField.class)
    String value() default "";

    @AliasFor(annotation = EasySmartField.class)
    String name() default "";

    @AliasFor(annotation = EasySmartField.class, attribute = "type")
    String type() default "";

    @AliasFor(annotation = EasySmartField.class, attribute = "comment")
    String comment() default "";
}

