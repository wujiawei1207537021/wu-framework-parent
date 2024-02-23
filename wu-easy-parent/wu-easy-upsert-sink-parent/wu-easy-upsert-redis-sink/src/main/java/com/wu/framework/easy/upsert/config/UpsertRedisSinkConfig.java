package com.wu.framework.easy.upsert.config;

import com.wu.framework.easy.upsert.RedisUpsertSink;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.dynamic.aop.EasyUpsertRedisAnnotationAdvisor;
import com.wu.framework.easy.upsert.dynamic.aop.QuickEasyUpsertRedisAnnotationAdvisor;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/11 2:45 下午
 */
@ConditionalOnProperty(prefix = "spring.data.redis", value = "host")
@Import({RedisUpsertSink.class})
public class UpsertRedisSinkConfig {

    @Bean
    @ConditionalOnMissingBean
    public EasyUpsertRedisAnnotationAdvisor easyUpsertRedisAnnotationAdvisor(LazyRedisTemplate lazyRedisTemplate) {
        return new EasyUpsertRedisAnnotationAdvisor(lazyRedisTemplate);
    }


    @Bean
    @ConditionalOnMissingBean
    public QuickEasyUpsertRedisAnnotationAdvisor quickEasyUpsertRedisAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert, LazyRedisTemplate lazyRedisTemplate) {
        return new QuickEasyUpsertRedisAnnotationAdvisor(abstractDynamicEasyUpsert, lazyRedisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public LazyRedisTemplate<String, String> lazyRedisTemplate(RedisConnectionFactory factory, RedisProperties redisProperties) {
        LazyRedisTemplate<String, String> template = new LazyRedisTemplate<>(factory, redisProperties);
        template.setConnectionFactory(factory);
        final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
        template.setValueSerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
