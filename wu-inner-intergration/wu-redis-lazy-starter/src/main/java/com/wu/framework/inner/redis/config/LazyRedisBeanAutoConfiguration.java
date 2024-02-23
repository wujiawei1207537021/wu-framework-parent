package com.wu.framework.inner.redis.config;


import com.wu.framework.inner.redis.dynamic.DynamicRedisAdapter;
import com.wu.framework.inner.redis.dynamic.LazyDynamicRedisAdapter;
import com.wu.framework.inner.redis.dynamic.proxy.LazyRedisClientProxy;
import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.util.Map;

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyRedisBeanAutoConfiguration {


    /**
     * redis 适配器
     *
     * @param dataSourceMap 注入的redis客户端
     * @return 适配器
     */
    @Bean
    public DynamicRedisAdapter dynamicRedisAdapter(Map<String, RedisClient> dataSourceMap) {
        return new LazyDynamicRedisAdapter(dataSourceMap);
    }

    /**
     * redis 客户端代理对象
     *
     * @param dynamicRedisAdapter redis适配器
     * @return 代理客户端
     */
    @Bean
    public LazyRedisClientProxy lazyRedisClientProxy(DynamicRedisAdapter dynamicRedisAdapter) {
        return new LazyRedisClientProxy(dynamicRedisAdapter);
    }


}

