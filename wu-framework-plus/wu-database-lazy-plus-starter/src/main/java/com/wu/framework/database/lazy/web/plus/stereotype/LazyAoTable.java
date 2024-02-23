package com.wu.framework.database.lazy.web.plus.stereotype;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * description 微服务内部使用声明Ao
 *
 * @author Jia wei Wu
 * @date 2022/10/12 2:14 下午
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LazyTable
public @interface LazyAoTable {


    /**
     * 表名
     *
     * @return
     */
    @AliasFor(attribute = "tableName", annotation = LazyTable.class)
    String tableName() default "";
}
