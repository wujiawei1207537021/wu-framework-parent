package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

@ConditionalOnBean(value = DataSource.class)
public class LazyOperationProxyBeanConfig {

    @Bean
    public LazyOperation lazyOperation(LazyOperationProxy lazyOperationProxy) {
        return (LazyOperation) Proxy.newProxyInstance(LazyOperation.class.getClassLoader(), new Class[]{LazyOperation.class}, lazyOperationProxy);
    }

}

