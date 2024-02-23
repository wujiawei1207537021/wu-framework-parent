package com.wu.freamwork.test.config;

import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.isDeleted.IsDeletedSqlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LazyInterceptorConfig {
    @Bean
    IsDeletedSqlInterceptor isDeletedSqlInterceptor(){
        return new IsDeletedSqlInterceptor(new DefalutLazyIsDeletedLineHandler());
    }
}
