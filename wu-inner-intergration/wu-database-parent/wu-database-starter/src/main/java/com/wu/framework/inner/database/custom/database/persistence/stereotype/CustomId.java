package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@CustomTableFile(indexType = CustomTableFile.CustomTableFileIndexType.AUTOMATIC,comment = "主键")
public @interface CustomId {
    @AliasFor(annotation = CustomTableFile.class,attribute = "value")
    String value() default "";

    @AliasFor(annotation = CustomTableFile.class,attribute = "name")
    String name() default "";


}
