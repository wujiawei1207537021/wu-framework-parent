package com.wu.framework.inner.lazy.database.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : xml 扫描
 * @date : 2020/7/5 下午2:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CustomRepositoryXmlScan {

    @AliasFor(attribute = "name")
    String[] value() default "";

    @AliasFor(attribute = "value")
    String[] name() default "";

    enum ExecuteType {
        INSERT,
        UPDATE,
        SELECT,
        DELETE,
        INSERT_BATCH,
        UPDATE_BATCH,
        SELECT_BATCH,
        DELETE_BATCH;

    }

}
