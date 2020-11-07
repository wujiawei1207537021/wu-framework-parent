package com.wu.framework.easy.stereotype.upsert.dynamic;

import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 自定义一数据源切换(Kafka、MySQL多数据源-mybatis)
 *
 * @param
 * @author Jia wei Wu
 * @return
 * @exception/throws
 * @date 2020/9/11 上午9:17
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface EasyUpsertDS {
    /**
     * 数据源名称(MYSQL多数据源有效)
     *
     * @return
     */
    @AliasFor(attribute = "name")
    String value() default "";

    @AliasFor(attribute = "value")
    String name() default "";

    /**
     * 数据源类型 默认MySQL
     */
    EasyUpsertType type() default EasyUpsertType.AUTO;

}
