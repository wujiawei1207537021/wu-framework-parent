package com.wu.framework.easy.upsert;

import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.inner.redis.annotation.LazyRedis;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@EasyUpsert(type = EasyUpsertType.REDIS)
@LazyRedis
public @interface EasyUpsertRedis {

    @AliasFor(attribute = "database", annotation = LazyRedis.class)
    int database() default 0;
}
