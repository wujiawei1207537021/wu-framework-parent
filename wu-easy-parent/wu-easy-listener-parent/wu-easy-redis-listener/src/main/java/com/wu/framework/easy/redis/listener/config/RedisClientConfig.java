package com.wu.framework.easy.redis.listener.config;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * describe : jedis config
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/12/16 9:05 下午
 */
@ConditionalOnProperty(prefix = "spring.data.redis", value = "host")
public class RedisClientConfig {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedisClient redisClient(RedisProperties redisProperties) {

        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        String password = redisProperties.getPassword();
        int database = redisProperties.getDatabase();
//        org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce lettuce = redisProperties.getLettuce();
        RedisURI.Builder builder = RedisURI.builder()                // <1> 创建单机连接的连接信息
                .withHost(host)
                .withPort(port)
                .withDatabase(database)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS));
        if (!ObjectUtils.isEmpty(password)) {
            builder
                    .withPassword(password.toCharArray());
        }
        RedisURI redisUri = builder
                .build();
        return RedisClient.create(redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection(RedisClient redisClient) {
        return redisClient.connectPubSub();
    }

}
