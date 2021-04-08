package com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype;

import com.wu.framework.inner.layer.stereotype.LayerField;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField(exist = true)
public @interface HBaseTableUnique {
    /**
     * 字段索引类型(数据库)
     */
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.FILE_TYPE;

    @AliasFor(annotation = LayerField.class,value = "exist")
    boolean exist() default true;

}
