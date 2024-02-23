package com.wu.framework.easy.upsert.config;

import com.wu.framework.easy.upsert.RedisSearchUpsertSink;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.dynamic.aop.EasyUpsertRedisSearchAnnotationAdvisor;
import com.wu.framework.easy.upsert.dynamic.aop.QuickEasyUpsertRediSearchAnnotationAdvisor;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import io.redisearch.client.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.time.Duration;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/11 2:45 下午
 */
@ConditionalOnProperty(prefix = "spring.data.redis.search", value = {"host"})
@Import({RedisSearchUpsertSink.class})
public class UpsertRedisSearchSinkConfig {

    @Bean
    @ConditionalOnMissingBean
    public EasyUpsertRedisSearchAnnotationAdvisor easyUpsertRedisSearchAnnotationAdvisor(LazyRedisTemplate lazyRedisTemplate) {
        return new EasyUpsertRedisSearchAnnotationAdvisor(lazyRedisTemplate);
    }


    @Bean
    @ConditionalOnMissingBean
    public QuickEasyUpsertRediSearchAnnotationAdvisor quickEasyUpsertRediSearchAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert, LazyRedisTemplate lazyRedisTemplate) {
        return new QuickEasyUpsertRediSearchAnnotationAdvisor(abstractDynamicEasyUpsert, lazyRedisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public Client client(RedisSearchProperties redisSearchProperties) {
        final int database = redisSearchProperties.getDatabase();
        final String password = redisSearchProperties.getPassword();
        final String host = redisSearchProperties.getHost();
        final int port = redisSearchProperties.getPort();
        final Duration timeout = redisSearchProperties.getTimeout();

        final Client client = new Client("", host, port, 1000, 20, password);
        return client;
    }


}
