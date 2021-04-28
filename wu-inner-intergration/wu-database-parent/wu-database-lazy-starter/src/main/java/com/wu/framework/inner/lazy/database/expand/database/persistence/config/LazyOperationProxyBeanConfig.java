package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import com.mysql.cj.jdbc.MysqlDataSource;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;


public class LazyOperationProxyBeanConfig {


    /**
     * @param
     * @return
     * @describe 延迟操作代理
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = DataSource.class)
    @Bean
    public LazyOperation lazyOperation(LazyOperationProxy lazyOperationProxy) {
        return (LazyOperation) Proxy.newProxyInstance(LazyOperation.class.getClassLoader(), new Class[]{LazyOperation.class}, lazyOperationProxy);
    }


//    /**
//    * @describe 完美的惰性操作代理
//    * @param
//    * @return
//    * @author Jia wei Wu
//    * @date 2021/4/17 7:34 下午
//    **/
//    @Bean
//    public PerfectLazyOperation perfectLazyOperationProxy(PerfectLazyOperationProxy perfectLazyOperationProxy) {
//        return (PerfectLazyOperation) Proxy.newProxyInstance(PerfectLazyOperation.class.getClassLoader(), new Class[]{PerfectLazyOperation.class}, perfectLazyOperationProxy);
//    }


}

