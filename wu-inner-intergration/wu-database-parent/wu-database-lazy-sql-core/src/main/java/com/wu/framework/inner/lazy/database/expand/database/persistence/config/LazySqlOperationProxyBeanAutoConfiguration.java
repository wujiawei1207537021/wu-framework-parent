package com.wu.framework.inner.lazy.database.expand.database.persistence.config;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

/**
 * mysql 懒人操作bean注入（包含代理和接口实现的bean）
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//@ConditionalOnBean(value = DataSource.class)
public class LazySqlOperationProxyBeanAutoConfiguration {

    /**
     * @param lazyOperationProxy 自定义接口实现方法执行代理类 包含执行 DDL、DQL、DML
     * @return describe 延迟操作代理
     * @author Jia wei Wu
     * @date 2021/4/17 7:34 下午
     **/
//    @ConditionalOnBean(value = {LazyOperationProxy.class})  // TODO
//    @ConditionalOnMissingBean(value = LazySqlOperation.class)
//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public LazySqlOperation lazySqlOperation(LazyOperationProxy lazyOperationProxy) {
//        return (LazySqlOperation) Proxy.newProxyInstance(LazySqlOperation.class.getClassLoader(), new Class[]{LazySqlOperation.class}, lazyOperationProxy);
//    }


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


}

