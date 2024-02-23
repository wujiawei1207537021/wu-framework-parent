package com.wu.framework.easy.redis.listener.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

/**
 * describe : jedis config
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/12/16 9:05 下午
 */
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
public class JedisConfig {

    @Bean
    @ConditionalOnMissingBean(value = Jedis.class)
    public Jedis jedis(RedisProperties redisProperties) {
        Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort(), redisProperties.isSsl());
        jedis.auth(redisProperties.getPassword());
        final int database = redisProperties.getDatabase();
        jedis.select(database);
        return jedis;
    }

}
