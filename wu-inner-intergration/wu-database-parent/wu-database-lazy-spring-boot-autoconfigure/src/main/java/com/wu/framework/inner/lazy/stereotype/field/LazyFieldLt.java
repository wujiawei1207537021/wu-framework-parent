package com.wu.framework.inner.lazy.stereotype.field;

import com.wu.framework.inner.layer.data.NormalUsedString;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * describe : 字段小于
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/31 00:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LazyFieldCondition(condition = NormalUsedString.LEFT_CHEV)
public @interface LazyFieldLt {

    /**
     * 是否忽视空
     *
     * @return
     */
    @AliasFor(annotation = LazyFieldCondition.class, attribute = "ignoreEmpty")
    boolean ignoreEmpty() default true;

    /**
     * 是否忽视
     */
    @AliasFor(annotation = LazyFieldCondition.class, attribute = "ignore")
    boolean ignore() default false;

}
