package com.wu.framework.inner.layer.stereotype;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 层字段注解
 *
 * @param
 * @author Jia wei Wu
 * @return
 * @exception/throws
 * @date 2021/4/1 下午2:05
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface LayerField {

    boolean exist() default true;

    /**
     * 字段名(默认驼峰)
     */
    @AliasFor(attribute = "value")
    String name() default "";

    @AliasFor(attribute = "name")
    String value() default "";

    /**
     * 字段索引类型
     *
     * @return
     */
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.FIELD_TYPE;

    enum LayerFieldType {
        // 字段类型
        FIELD_TYPE,
        ID,
        // 唯一性索引
        UNIQUE,
        // 自动的
        AUTOMATIC;
    }
}
