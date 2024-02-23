package com.wu.framework.easy.upsert;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyUpsertRedis
public @interface QuickEasyUpsertRedis {

    @AliasFor(attribute = "database", annotation = EasyUpsertRedis.class)
    int database() default 0;
}
