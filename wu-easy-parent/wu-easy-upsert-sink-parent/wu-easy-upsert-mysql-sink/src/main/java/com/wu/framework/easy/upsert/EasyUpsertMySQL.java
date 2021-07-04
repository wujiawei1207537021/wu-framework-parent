package com.wu.framework.easy.upsert;


import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyDS;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @describe: 切换MySQL数据源
 * @author : Jia wei Wu
 * @date : 2021/7/4 7:37 下午
 * @version : 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyUpsert(type = EasyUpsertType.MySQL)
@LazyDS
public @interface EasyUpsertMySQL {
    /**
     * 数据源名称(MYSQL多数据源有效)
     *
     * @return
     */
    @AliasFor(attribute = "name",annotation = LazyDS.class)
    String value() default "";

    @AliasFor(attribute = "value",annotation = LazyDS.class)
    String name() default "";
}
