package com.wu.framework.inner.layer.data.lock.config;

import com.wu.framework.inner.layer.data.lock.DefaultLock;
import com.wu.framework.inner.layer.data.lock.ILock;
import com.wu.framework.inner.layer.data.lock.aop.MonitorEasyLock;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/03 8:21 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LayerEnableAutoLockConfiguration {
    @Bean
    @ConditionalOnMissingBean(ILock.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    ILock iLock() {
        return new DefaultLock();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    MonitorEasyLock.MonitorEasyLockInterceptor monitorEasyLockInterceptor(ILock iLock) {
        return new MonitorEasyLock.MonitorEasyLockInterceptor(iLock);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    MonitorEasyLock monitorEasyLock(MonitorEasyLock.MonitorEasyLockInterceptor monitorEasyLockInterceptor) {
        return new MonitorEasyLock(monitorEasyLockInterceptor);
    }
}
