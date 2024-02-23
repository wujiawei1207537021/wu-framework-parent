package com.wu.framework.easy.upsert.autoconfigure.dynamic;

import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 自定义一数据源切换(Kafka、MySQL)
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
public @interface EasyUpsert {

    /**
     * 数据源类型 默认AUTO
     */
    EasyUpsertType type() default EasyUpsertType.AUTO;

}
