package com.wu.framework.inner.lazy.config.prop;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;

/**
 * description 懒人数据源配置 (可选)
 * 默认使用 MysqlDataSource
 *
 * @author Jia wei Wu
 * @date 2021/4/23 下午1:39
 */
@Slf4j
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", value = "url")
@ConfigurationProperties(prefix = "spring.datasource")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyDataSourceProperties {

    /**
     * 别名
     */
    private String alias = "master";

    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    /**
     * Fully qualified name of the connection pool implementation to use. By default, it
     * is auto-detected from the classpath.
     */
    private Class<? extends DataSource> type;
    /**
     * 数据库类型兼容建表
     */
    private LazyDataSourceType lazyDataSourceType;

    /**
     * 选择 驱动
     *
     * @return 驱动
     */
    public String switchDriverClassName() {
        if (ObjectUtils.isEmpty(driverClassName)) {
            LazyDataSourceType lazyDataSourceType = getLazyDataSourceType();
            return lazyDataSourceType.getDriver();
        }
        return driverClassName;
    }

    public LazyDataSourceType getLazyDataSourceType() {
        if (ObjectUtils.isEmpty(lazyDataSourceType)) {
            this.lazyDataSourceType = SourceFactory.getLazyDataSourceType(url);
        }
        return lazyDataSourceType;
    }

    /**
     * Initialize a {@link DataSourceBuilder} with the state of this instance.
     *
     * @return a {@link DataSourceBuilder} initialized with the customizations defined on
     * this instance
     */
    public DataSourceBuilder<?> initializeDataSourceBuilder() {
        return DataSourceBuilder.create()
                .type(switchType())
                .driverClassName(switchDriverClassName())
                .url(getUrl())
                .username(getUsername())
                .password(getPassword());
    }

    /**
     * 选择数据源类型
     *
     * @return
     */
    public Class<? extends DataSource> switchType() {
        if (ObjectUtils.isEmpty(type)) {
            try {
                LazyDataSourceType dataSourceType = getLazyDataSourceType();
                String dataSourceClass = dataSourceType.getDataSourceClass();
                Class<?> clazz = Class.forName(dataSourceClass);
                return (Class<? extends DataSource>) clazz;
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("无法初始化数据源，请配置数据源类型: spring.datasource.type='',当前配置：" + this);
            }
        }
        return type;
    }
}
