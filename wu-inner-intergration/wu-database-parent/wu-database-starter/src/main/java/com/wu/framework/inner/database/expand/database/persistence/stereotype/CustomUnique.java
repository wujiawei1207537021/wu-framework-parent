package com.wu.framework.inner.database.expand.database.persistence.stereotype;

import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 唯一索引
 * @date : 2020/7/4 下午8:25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyTableField(indexType = EasyTableField.TableFileIndexType.UNIQUE)
public @interface CustomUnique {

    @AliasFor(annotation = EasyTableField.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = EasyTableField.class, attribute = "name")
    String name() default "";

}
