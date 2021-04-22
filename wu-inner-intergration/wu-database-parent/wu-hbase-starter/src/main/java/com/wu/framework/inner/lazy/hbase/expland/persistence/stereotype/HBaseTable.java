package com.wu.framework.inner.lazy.hbase.expland.persistence.stereotype;

import com.wu.framework.inner.layer.stereotype.LayerClass;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Indexed
@LayerClass
public @interface HBaseTable {

    String namespace() default "default";

    String tableName() default "";

    String  columnFamily() default "";

    boolean perfectTable() default false;

}
