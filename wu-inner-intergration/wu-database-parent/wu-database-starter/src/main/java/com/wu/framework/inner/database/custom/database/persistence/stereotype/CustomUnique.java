package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
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
@EasyTableFile(indexType = EasyTableFile.CustomTableFileIndexType.UNIQUE)
public @interface CustomUnique {

    @AliasFor(annotation = EasyTableFile.class,attribute = "value")
    String value() default "";

    @AliasFor(annotation = EasyTableFile.class,attribute = "name")
    String name() default "";

}
