//package com.wu.framework.inner.dynamic.database.component.aop;
//
//import com.wu.framework.inner.dynamic.database.component.CDS;
//import com.wu.framework.inner.dynamic.database.config.DynamicDatabaseConfig;
//import com.wu.framework.inner.lazy.database.datasource.proxy.LazyDataSource;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//
///**
// * @author : Jia wei Wu
// * @version 1.0
// * describe : 多数据源切换  MultiDataSourceSwitching
// * @date : 2020/8/28 下午10:13
// */
//@Aspect
//@AutoConfigureAfter(value = {DynamicDatabaseConfig.class})
//@ConditionalOnBean(value = DynamicDatabaseConfig.class)
//@ConditionalOnProperty(prefix = "spring.wu.dynamic.database", value = "enable", havingValue = "true")
//public class MultiDataSourceSwitching implements EasyDataSourceAdapter, InitializingBean {
//
//    private final Logger logger = LoggerFactory.getLogger(MultiDataSourceSwitching.class);
//    private final DynamicDatabaseConfig dynamicDatabaseConfig;
//    // 当前使用的数据源
//    private LazyDataSource lazyProxyDataSource;
//
//    public MultiDataSourceSwitching(DynamicDatabaseConfig dynamicDatabaseConfig) {
//        this.dynamicDatabaseConfig = dynamicDatabaseConfig;
//    }
//
//    @Pointcut("@annotation(CDS)")
//    public void pointcutCustomDB(CDS CDS) {
//    }
//
//
//    @Before("pointcutCustomDB(CDS)")
//    public void customDBBefore(JoinPoint point, CDS CDS) {
//        String name = CDS.value();
//        this.lazyProxyDataSource =
//                dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.getOrDefault(name,
//                        dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.values().iterator().next());
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        logger.info("init custom_db success");
//    }
//
//    @Override
//    public LazyDataSource getEasyDataSource() {
//        if (null == lazyProxyDataSource) {
//            return dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.values().iterator().next();
//        }
//        return lazyProxyDataSource;
//    }
//}
