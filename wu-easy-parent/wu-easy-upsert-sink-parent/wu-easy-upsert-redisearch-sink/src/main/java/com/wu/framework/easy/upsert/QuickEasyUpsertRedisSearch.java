package com.wu.framework.easy.upsert;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyUpsertRedisSearch
public @interface QuickEasyUpsertRedisSearch {

    @AliasFor(attribute = "database", annotation = EasyUpsertRedisSearch.class)
    int database() default 0;
}
