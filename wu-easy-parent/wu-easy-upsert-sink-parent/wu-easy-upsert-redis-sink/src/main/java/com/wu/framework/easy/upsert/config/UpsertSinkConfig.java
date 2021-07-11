package com.wu.framework.easy.upsert.config;

import com.wu.framework.easy.upsert.RedisUpsertSink;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.dynamic.aop.EasyUpsertRedisAnnotationAdvisor;
import com.wu.framework.easy.upsert.dynamic.aop.QuickEasyUpsertRedisAnnotationAdvisor;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/7/11 2:45 下午
 */
@Import({RedisUpsertSink.class})
public class UpsertSinkConfig {

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
}
