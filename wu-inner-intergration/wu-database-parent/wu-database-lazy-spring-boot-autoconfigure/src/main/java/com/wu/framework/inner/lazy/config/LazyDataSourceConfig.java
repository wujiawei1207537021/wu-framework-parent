package com.wu.framework.inner.lazy.config;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDataSourceConfig {


    /**
     * 默认数据库源分类
     */
    static Map<String, String> DEFAULT_DATABASE_SOURCE_CLASSIFICATION = new HashMap<String, String>() {
        {
            put("org.h2.Driver", "org.h2.jdbcx.JdbcDataSource");
            put("com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlDataSource");
        }
    };

    /**
     * description 添加 H2 懒人数据源配置
     *
     * @param dataSourceProperties
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/23 下午1:54
     */
    @Deprecated
    @Order(value = 1)
    @ConditionalOnClass(name = "org.h2.Driver")
    @Bean(name = "lazyDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource", value = "driver-class-name", havingValue = "org.h2.Driver")
    @ConditionalOnMissingBean(DataSource.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DataSource lazyH2DataSource(LazyDataSourceProperties dataSourceProperties) throws ClassNotFoundException {
        Class<DataSource> dataSourceType = (Class<DataSource>) Class.forName("org.h2.jdbcx.JdbcDataSource");
        DataSourceBuilder build = DataSourceBuilder.create().type(dataSourceType);
        build.driverClassName(dataSourceProperties.getDriverClassName());
        build.url(dataSourceProperties.getUrl());
        build.username(dataSourceProperties.getUsername());
        build.password(dataSourceProperties.getPassword());
        LazyDatabaseJsonMessage.lazyDataSourceType = LazyDataSourceType.H2;
        return build.build();
    }

//    /**
//     * description 添加mysql懒人数据源配置
//     *
//     * @param dataSourceProperties
//     * @return
//     * @exception/throws
//     * @author Jia wei Wu
//     * @date 2021/4/23 下午1:54
//     */
//    @Order(value = 2)
//    @ConditionalOnClass(name = "com.mysql.cj.jdbc.Driver")
//    @Bean(name = "lazyMysqlDataSource")
//    @ConditionalOnMissingBean(DataSource.class)
//    @ConditionalOnProperty(prefix = "spring.datasource", value = "driver-class-name", havingValue = "com.mysql.cj.jdbc.Driver")
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public DataSource lazyMysqlDataSource(LazyDataSourceProperties dataSourceProperties) {
//        DataSource build = dataSourceProperties.initializeDataSourceBuilder().build();
//        LazyDatabaseJsonMessage.lazyDataSourceType = LazyDataSourceType.MySQL;
//        return build;
//    }


    /**
     * description 添加懒人数据源配置
     *
     * @param
     * @param dataSourceProperties 数据源连接配置
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/23 下午1:54
     */
    @Bean(name = "lazyDataSource")
    @ConditionalOnMissingBean(DataSource.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DataSource lazyDataSource(LazyDataSourceProperties dataSourceProperties) {
        LazyDataSourceType lazyDataSourceType = dataSourceProperties.getLazyDataSourceType();
        if (SourceFactory.defaultLazyDataSourceType == null) {
            SourceFactory.defaultLazyDataSourceType = lazyDataSourceType;
        }
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


}
