package com.wu.framework.easy.stereotype.upsert.dynamic;

import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * description 快速插入策略
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午10:24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface EasyUpsertStrategy {

    @AliasFor(attribute = "easyUpsertType")
    EasyUpsertType value() default EasyUpsertType.EMPTY;

    @AliasFor(attribute = "value")
    EasyUpsertType easyUpsertType() default EasyUpsertType.EMPTY;
}
