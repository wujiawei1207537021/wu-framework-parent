package com.wu.framework.inner.lazy.config.prop;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * description 懒人数据源配置 (可选)
 * 默认使用 MysqlDataSource
 *
 * @author Jia wei Wu
 * @date 2021/4/23 下午1:39
 */
@Data
@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", value = "url")
@ConfigurationProperties(prefix = "spring.datasource")
public class LazyDataSourceProperties {

    /**
     * 别名
     */
    private String alias;

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

}
