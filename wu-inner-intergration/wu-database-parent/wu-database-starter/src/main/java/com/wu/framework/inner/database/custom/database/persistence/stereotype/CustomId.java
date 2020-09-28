package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyTableFile(indexType = EasyTableFile.CustomTableFileIndexType.AUTOMATIC,comment = "主键")
public @interface CustomId {
    @AliasFor(annotation = EasyTableFile.class,attribute = "value")
    String value() default "";

    @AliasFor(annotation = EasyTableFile.class,attribute = "name")
    String name() default "";


}
