package com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
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
@EasySmartField(indexType = EasySmartField.TableFileIndexType.UNIQUE)
public @interface CustomUnique {

    @AliasFor(annotation = EasySmartField.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = EasySmartField.class, attribute = "name")
    String name() default "";

}
