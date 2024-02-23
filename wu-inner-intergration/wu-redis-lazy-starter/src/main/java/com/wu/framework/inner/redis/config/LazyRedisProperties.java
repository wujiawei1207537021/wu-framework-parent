package com.wu.framework.inner.redis.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;


@ConfigurationProperties(
        prefix = "spring.data.redis"
)
@Data
public class LazyRedisProperties {
    private final Jedis jedis = new Jedis();
    private final Lettuce lettuce = new Lettuce();
    private String alias = "master";
    private int database = 0;
    private String url;
    private String host = "localhost";
    private String username;
    private String password;
    private int port = 6379;
    private boolean ssl;
    private Duration timeout;
    private Duration connectTimeout;
    private String clientName;
    private ClientType clientType;
    private Sentinel sentinel;
    private Cluster cluster;
}