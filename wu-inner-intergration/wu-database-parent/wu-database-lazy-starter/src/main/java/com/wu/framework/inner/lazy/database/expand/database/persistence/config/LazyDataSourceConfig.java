package com.wu.framework.inner.lazy.database.expand.database.persistence.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

/**
 * description 懒人数据源配置 (可选)
 * 默认使用 MysqlDataSource
 *
 * @author Jia wei Wu
 * @date 2021/4/23 下午1:39
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", value = "url")
public class LazyDataSourceConfig {

    /**
     * description 添加懒人数据源配置
     *
     * @param
     * @param dataSourceProperties
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/23 下午1:54
     */
    @Bean(name = "lazyDataSource")
    public DataSource lazyDataSource(LazyDataSourceProperties dataSourceProperties) {
        MysqlDataSource build = DataSourceBuilder.create().type(MysqlDataSource.class).build();
        build.setUrl(dataSourceProperties.getUrl());
        build.setUser(dataSourceProperties.getUsername());
        build.setPassword(dataSourceProperties.getPassword());
        return build;
    }
}
