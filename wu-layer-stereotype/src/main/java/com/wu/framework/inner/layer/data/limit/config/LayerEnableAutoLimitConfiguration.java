package com.wu.framework.inner.layer.data.limit.config;

import com.wu.framework.inner.layer.data.limit.DefaultIAccessLimit;
import com.wu.framework.inner.layer.data.limit.IAccessLimit;
import com.wu.framework.inner.layer.data.limit.aop.MonitorEasyAccessLimit;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/10/09 6:18 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LayerEnableAutoLimitConfiguration {

    @Bean
    @ConditionalOnMissingBean(IAccessLimit.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    IAccessLimit iAccessLimit() {
        return new DefaultIAccessLimit();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnMissingBean(MonitorEasyAccessLimit.MonitorEasyAccessLimitInterceptor.class)
    @ConditionalOnBean(IAccessLimit.class)
    MonitorEasyAccessLimit.MonitorEasyAccessLimitInterceptor monitorEasyAccessLimitInterceptor(IAccessLimit iAccessLimit) {
        return new MonitorEasyAccessLimit.MonitorEasyAccessLimitInterceptor(iAccessLimit);
    }

    @Bean
    @ConditionalOnMissingBean(MonitorEasyAccessLimit.class)
    @ConditionalOnBean(MonitorEasyAccessLimit.MonitorEasyAccessLimitInterceptor.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    MonitorEasyAccessLimit monitorEasyAccessLimit(MonitorEasyAccessLimit.MonitorEasyAccessLimitInterceptor monitorEasyAccessLimitInterceptor) {
        return new MonitorEasyAccessLimit(monitorEasyAccessLimitInterceptor);
    }


}
