package com.wu.framework.easy.upsert.autoconfigure.dynamic;

import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 自定义快速一数据源切换(Kafka、MySQL多数据源-mybatis)
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
@EasyUpsert
public @interface QuickEasyUpsert {

    /**
     * 数据源类型 默认MySQL
     */
    @AliasFor(attribute = "type", annotation = EasyUpsert.class)
    EasyUpsertType type() default EasyUpsertType.AUTO;

}
