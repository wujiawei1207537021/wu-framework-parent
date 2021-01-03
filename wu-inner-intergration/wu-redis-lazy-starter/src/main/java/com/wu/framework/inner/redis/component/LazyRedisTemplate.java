package com.wu.framework.inner.redis.component;


import com.wu.framework.inner.redis.LazyRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Description   :  获取redis不同数据库的连接对象
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/11/14 0014 10:14
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/11/14 0014 10:14
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */
@Slf4j
public class LazyRedisTemplate extends StringRedisTemplate implements LazyRedis {

    protected Map<Integer, RedisConnectionFactory> redisConnectionFactoryMap = new ConcurrentHashMap<>(20);
    private final Integer MASTER;
    private Integer dyDatabase;
    private final RedisProperties redisProperties;

    public LazyRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory, RedisProperties redisProperties) {
        MASTER = lettuceConnectionFactory.getDatabase();
        this.redisProperties = redisProperties;
        dyDatabase = MASTER;
        redisConnectionFactoryMap.put(MASTER, lettuceConnectionFactory);
    }


    @Override
    public RedisConnectionFactory getConnectionFactory() {
        int database = getDyDatabase();
        try {
            return getConnectionFactory(database);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("无法找到redis数据库 %s 使用redis 默认配置数据库%n", database);
            return getConnectionFactory(MASTER);
        }
    }

    public RedisConnectionFactory getConnectionFactory(int database) {
        if (redisConnectionFactoryMap.containsKey(database)) {
            return redisConnectionFactoryMap.get(database);
        }
        //redis配置
        RedisStandaloneConfiguration redisConfiguration = new
                RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        redisConfiguration.setDatabase(database);
        redisConfiguration.setPassword(redisProperties.getPassword());
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration, builder.build());
        lettuceConnectionFactory.afterPropertiesSet();
        redisConnectionFactoryMap.put(database, lettuceConnectionFactory);
        return lettuceConnectionFactory;
    }

    public Integer getDyDatabase() {
        return dyDatabase;
    }

    public void setDyDatabase(Integer dyDatabase) {
        this.dyDatabase = dyDatabase;
    }


}
