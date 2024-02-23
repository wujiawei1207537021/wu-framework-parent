package com.wu.framework.inner.redis.config;

import com.wu.framework.inner.redis.aop.LazyRedisDBAOPAdvisor;
import com.wu.framework.inner.redis.component.LazyJedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.Jedis;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/4 5:06 下午
 */
@Import({LazyRedisTemplate.class})
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
public class LazyRedisConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(LazyRedisTemplate.class)
    public LazyRedisDBAOPAdvisor lazyRedisDBAOPAdvisor(LazyRedisTemplate lazyRedisTemplate) {
        final LazyRedisDBAOPAdvisor.LazyRedisDBInterceptor lazyRedisDBInterceptor = new LazyRedisDBAOPAdvisor.LazyRedisDBInterceptor(lazyRedisTemplate);
        return new LazyRedisDBAOPAdvisor(lazyRedisDBInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public Jedis jedis(RedisProperties redisProperties) {
        Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
        jedis.auth(redisProperties.getPassword());
        final int database = redisProperties.getDatabase();
        jedis.select(database);
        return jedis;
    }

    @Bean
    @ConditionalOnMissingBean
    public LazyJedis lazyJedis(RedisProperties redisProperties) {
        LazyJedis jedis = new LazyJedis(redisProperties.getHost(), redisProperties.getPort());
        jedis.auth(redisProperties.getPassword());
        final int database = redisProperties.getDatabase();
        jedis.select(database);
        return jedis;
    }
}
