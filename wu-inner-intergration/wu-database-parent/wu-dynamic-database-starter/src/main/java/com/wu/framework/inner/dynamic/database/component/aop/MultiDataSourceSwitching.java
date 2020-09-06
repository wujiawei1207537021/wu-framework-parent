package com.wu.framework.inner.dynamic.database.component.aop;

import com.wu.framework.inner.database.CustomDataSource;
import com.wu.framework.inner.database.CustomDataSourceAdapter;
import com.wu.framework.inner.database.config.ICustomDatabaseScanBean;
import com.wu.framework.inner.dynamic.database.component.CDS;
import com.wu.framework.inner.dynamic.database.config.DynamicDatabaseConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 多数据源切换  MultiDataSourceSwitching
 * @date : 2020/8/28 下午10:13
 */
@Aspect
@AutoConfigureAfter(value = {DynamicDatabaseConfig.class})
@ConditionalOnBean(value = DynamicDatabaseConfig.class)
@ConditionalOnProperty(prefix = "spring.wu.dynamic.database",value = "enable",havingValue = "true")
public class MultiDataSourceSwitching implements CustomDataSourceAdapter, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(MultiDataSourceSwitching.class);

    // 当前使用的数据源
    private CustomDataSource customDataSource;
    private final DynamicDatabaseConfig dynamicDatabaseConfig;

    public MultiDataSourceSwitching(DynamicDatabaseConfig dynamicDatabaseConfig,  ICustomDatabaseScanBean iCustomDatabaseScanBean) {
        this.dynamicDatabaseConfig = dynamicDatabaseConfig;
        //TODO 此处注入是因为配置文件无法扫描
    }

    @Pointcut("@annotation(CDS)")
    public void pointcutCustomDB(CDS CDS) {
    }


    @Before("pointcutCustomDB(CDS)")
    public void customDBBefore(JoinPoint point, CDS CDS) {
        String name = CDS.value();
        this.customDataSource =
                dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.getOrDefault(name,
                        dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.values().iterator().next());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("init custom_db success");
    }

    @Override
    public CustomDataSource getCustomDataSource() {
        if(null==customDataSource){
            return dynamicDatabaseConfig.CUSTOM_DATA_SOURCE_MAP.values().iterator().next();
        }
        return customDataSource;
    }
}
