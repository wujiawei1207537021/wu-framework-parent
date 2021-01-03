package com.wu.framework.inner.redis.aop;


import com.wu.framework.inner.redis.annotation.DynamicRedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DynamicRedisDBAOP {

    private final LazyRedisTemplate lazyRedisTemplate;

    public DynamicRedisDBAOP(LazyRedisTemplate lazyRedisTemplate) {
        this.lazyRedisTemplate = lazyRedisTemplate;
    }

    @Pointcut("@annotation(dynamicRedis)")
    public void redisCacheAOP(DynamicRedis dynamicRedis) {
        System.out.println("执行了类注解");
    }

    @Before("redisCacheAOP(dynamicRedis)")
    public void beforeExec(DynamicRedis dynamicRedis) {
        lazyRedisTemplate.setDyDatabase(dynamicRedis.database());
    }

}
