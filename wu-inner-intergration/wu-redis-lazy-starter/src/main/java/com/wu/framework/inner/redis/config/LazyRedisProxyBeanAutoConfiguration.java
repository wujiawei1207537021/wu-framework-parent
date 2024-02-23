package com.wu.framework.inner.redis.config;


import com.wu.framework.inner.redis.component.LazyRedisLettuce;
import com.wu.framework.inner.redis.dynamic.proxy.LazyRedisClientProxy;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import java.lang.reflect.Proxy;

/**
 * mysql 懒人操作bean注入（包含代理和接口实现的bean）
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//@ConditionalOnProperty(prefix = "spring.data.redis")
public class LazyRedisProxyBeanAutoConfiguration {

    @ConditionalOnMissingBean(value = LazyRedisLettuce.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyRedisLettuce lazyRedisLettuce(LazyRedisClientProxy lazyRedisClientProxy) {
        return (LazyRedisLettuce) Proxy.newProxyInstance(LazyRedisLettuce.class.getClassLoader(), new Class[]{LazyRedisLettuce.class}, lazyRedisClientProxy);
    }


}

