package com.wu.framework.inner.dynamic.database.config;

import com.wu.framework.inner.dynamic.database.DynamicLazyDSAdapter;
import com.wu.framework.inner.dynamic.database.component.aop.LazyDBAOPAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/4 6:07 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Import(DynamicLazyDSAdapter.class)
public class LazyDBConfig {

    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LazyDBAOPAdvisor lazyDBAOPAdvisor() {
        LazyDBAOPAdvisor.LazyDBInterceptor lazyDBInterceptor = new LazyDBAOPAdvisor.LazyDBInterceptor();
        return new LazyDBAOPAdvisor(lazyDBInterceptor);
    }
}
