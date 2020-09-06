package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @describe: 唯一索引
 * @author : 吴佳伟
 * @date : 2020/7/4 下午8:25
 * @version : 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@CustomTableFile(indexType = CustomTableFile.CustomTableFileIndexType.UNIQUE)
public @interface CustomUnique {

    @AliasFor(annotation = CustomTableFile.class,attribute = "value")
    String value() default "";

    @AliasFor(annotation = CustomTableFile.class,attribute = "name")
    String name() default "";

}
