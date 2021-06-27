package com.wu.framework.inner.redis.aop;


import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@ConditionalOnBean(LazyRedisTemplate.class)
@Aspect
public class LazyRedisDBAOP {

    private final LazyRedisTemplate lazyRedisTemplate;

    public LazyRedisDBAOP(LazyRedisTemplate lazyRedisTemplate) {
        this.lazyRedisTemplate = lazyRedisTemplate;
    }

    @Pointcut("@annotation(lazyRedis)")
    public void redisCacheAOP(LazyRedis lazyRedis) {
        System.out.println("执行了类注解");
    }

    @Before("redisCacheAOP(lazyRedis)")
    public void beforeExec(LazyRedis lazyRedis) {
        lazyRedisTemplate.setDyDatabase(lazyRedis.database());
    }

}
