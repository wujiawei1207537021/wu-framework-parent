package com.wu.framework.inner.lazy.database.dynamic.config;


import com.wu.framework.inner.lazy.database.dynamic.aop.LazyDynamicPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * 注入动态数据源切换 切面
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/7/4 6:07 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDynamicBeanConfig {

    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDynamicPointcutAdvisor lazyDynamicPointcutAdvisor() {
        LazyDynamicPointcutAdvisor.LazyDBInterceptor lazyDBInterceptor = new LazyDynamicPointcutAdvisor.LazyDBInterceptor();
        return new LazyDynamicPointcutAdvisor(lazyDBInterceptor);
    }
}
