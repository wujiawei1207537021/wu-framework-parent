package com.wu.framework.inner.layer.data;


import com.wu.framework.inner.layer.stereotype.LayerClass;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerClass
public @interface LayerData {

    /**
     * 数据下钻
     * @return
     */
    boolean dataDrillDown() default false;
}
