package com.wu.framework.inner.redis.aop;


import com.wu.framework.inner.redis.annotation.DynamicRedisDB;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Aspect
public class DynamicRedisDBAOP {

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(dynamicRedisDB)")
    public void redisCacheAOP(DynamicRedisDB dynamicRedisDB) {
        System.out.println("执行了类注解");
    }

    @Around("redisCacheAOP(dynamicRedisDB)")
    public void beforeExec(DynamicRedisDB dynamicRedisDB) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        factory.setDatabase(dynamicRedisDB.defaultBD());
        factory.resetConnection();
        redisTemplate.setConnectionFactory(factory);
    }

}
