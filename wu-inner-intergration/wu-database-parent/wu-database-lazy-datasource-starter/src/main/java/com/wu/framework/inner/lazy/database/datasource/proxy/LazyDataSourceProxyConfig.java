package com.wu.framework.inner.lazy.database.datasource.proxy;

import com.wu.framework.inner.lazy.database.datasource.proxy.util.LazyDataSourceProxyUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;

/**
 * description 懒人数据源配置信息
 *
 * @author 吴佳伟
 * @date 2023/05/22 20:39
 */
@ConditionalOnProperty(prefix = "spring.datasource", value = "url")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDataSourceProxyConfig {

    // TODO  兼容其他框架使用的数据源代理
    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    LazyProxyDataSource lazyProxyDataSource(DataSource dataSource) {
        return LazyDataSourceProxyUtils.proxy(dataSource);
    }

}
