package com.wu.framework.inner.redis.config;

import com.wu.framework.inner.redis.aop.LazyRedisDBAOPAdvisor;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/4 5:06 下午
 */
@Import({LazyRedisTemplate.class})
@ConditionalOnProperty(prefix = "spring.data.redis", value = "host")
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
    public RedisClient redisClient(LazyRedisProperties lazyRedisProperties) {

        String host = lazyRedisProperties.getHost();
        int port = lazyRedisProperties.getPort();
        String password = lazyRedisProperties.getPassword();
        int database = lazyRedisProperties.getDatabase();
        RedisProperties.Lettuce lettuce = lazyRedisProperties.getLettuce();
        RedisURI.Builder builder = RedisURI.builder()                // <1> 创建单机连接的连接信息
                .withHost(host)
                .withPort(port)
                .withDatabase(database)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS));
        if (!ObjectUtils.isEmpty(password)) {
            builder
                    .withPassword(password);
        }
        RedisURI redisUri = builder
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);   //
        return redisClient;
    }

}
