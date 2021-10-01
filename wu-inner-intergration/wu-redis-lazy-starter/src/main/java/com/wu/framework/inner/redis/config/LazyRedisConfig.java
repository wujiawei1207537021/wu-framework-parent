package com.wu.framework.inner.redis.config;

import com.wu.framework.inner.redis.aop.LazyRedisDBAOPAdvisor;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author : 吴佳伟
 * @version 1.0
 * describe :
 * @date : 2021/7/4 5:06 下午
 */
@Import({LazyRedisTemplate.class})
public class LazyRedisConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(LazyRedisTemplate.class)
    public LazyRedisDBAOPAdvisor lazyRedisDBAOPAdvisor(LazyRedisTemplate lazyRedisTemplate){
        final LazyRedisDBAOPAdvisor.LazyRedisDBInterceptor lazyRedisDBInterceptor = new LazyRedisDBAOPAdvisor.LazyRedisDBInterceptor(lazyRedisTemplate);
        return new LazyRedisDBAOPAdvisor(lazyRedisDBInterceptor);
    }
}
