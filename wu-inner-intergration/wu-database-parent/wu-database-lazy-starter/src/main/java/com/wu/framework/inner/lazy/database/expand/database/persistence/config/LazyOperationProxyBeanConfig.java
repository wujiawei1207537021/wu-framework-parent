package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.aop.LazyTransactionalAOPAdvisor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.LazyOperationProxy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;


public class LazyOperationProxyBeanConfig {


    /**
     * @param
     * @return describe 延迟操作代理
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyOperation.class)
    @Bean
    public LazyOperation lazyOperation(LazyOperationProxy lazyOperationProxy) {
        return (LazyOperation) Proxy.newProxyInstance(LazyOperation.class.getClassLoader(), new Class[]{LazyOperation.class}, lazyOperationProxy);
    }


//    /**
//    * describe 完美的惰性操作代理
//    * @param
//    * @return
//    * @author Jia wei Wu
//    * @date 2021/4/17 7:34 下午
//    **/
//    @Bean
//    public PerfectLazyOperation perfectLazyOperationProxy(PerfectLazyOperationProxy perfectLazyOperationProxy) {
//        return (PerfectLazyOperation) Proxy.newProxyInstance(PerfectLazyOperation.class.getClassLoader(), new Class[]{PerfectLazyOperation.class}, perfectLazyOperationProxy);
//    }

    /**
     * describe:  事务切面
     *
     * @author : Jia wei Wu
     * @date : 2022/1/1 4:28 下午
     * @version : 1.0
     */
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = LazyTransactionalAOPAdvisor.class)
    @Bean
    public LazyTransactionalAOPAdvisor lazyTransactionalAOPAdvisor(LazyOperationProxy lazyOperationProxy) {
        final LazyTransactionalAOPAdvisor.LazyTransactionalAOPAdvisorInterceptor lazyTransactionalAOPAdvisorInterceptor =
                new LazyTransactionalAOPAdvisor.LazyTransactionalAOPAdvisorInterceptor(lazyOperationProxy);
        LazyTransactionalAOPAdvisor lazyTransactionalAOPAdvisor =
                new LazyTransactionalAOPAdvisor(lazyTransactionalAOPAdvisorInterceptor);
        return lazyTransactionalAOPAdvisor;
    }

}

