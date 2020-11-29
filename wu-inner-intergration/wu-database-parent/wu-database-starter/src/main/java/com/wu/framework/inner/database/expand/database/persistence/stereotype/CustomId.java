package com.wu.framework.inner.database.expand.database.persistence.stereotype;

import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyTableField(indexType = EasyTableField.TableFileIndexType.AUTOMATIC, comment = "主键")
public @interface CustomId {
    @AliasFor(annotation = EasyTableField.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = EasyTableField.class, attribute = "name")
    String name() default "";


}
