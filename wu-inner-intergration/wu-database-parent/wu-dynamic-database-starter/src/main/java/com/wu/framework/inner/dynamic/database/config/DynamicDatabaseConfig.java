package com.wu.framework.inner.dynamic.database.config;

import com.wu.framework.inner.database.CustomDataSource;
import com.wu.framework.inner.database.SimpleCustomDataSource;
import com.wu.framework.inner.database.config.ICustomDatabaseConfiguration;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/8/26 下午8:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.wu.dynamic.database")
public class DynamicDatabaseConfig implements InitializingBean {

    public final Map<String, CustomDataSource> CUSTOM_DATA_SOURCE_MAP = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DynamicDatabaseConfig.class);
    private boolean enable = true;
    private Map<String, DatabaseConfig> databaseConfigMap = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        if (ObjectUtils.isEmpty(databaseConfigMap)) {
            throw new RuntimeException(" fail to init dynamic database for empty");
        }
        logger.info("init dynamic database config for {}", databaseConfigMap.keySet());
        for (Map.Entry<String, DatabaseConfig> stringDatabaseConfigEntry : databaseConfigMap.entrySet()) {
            CUSTOM_DATA_SOURCE_MAP.put(stringDatabaseConfigEntry.getKey(), new SimpleCustomDataSource(stringDatabaseConfigEntry.getValue()));
        }
    }

    @Data
    public static class DatabaseConfig implements ICustomDatabaseConfiguration {
        private Class driver;
        private String url;
        private String username;
        private String password;

        @Override
        public void afterPropertiesSet() throws Exception {

        }
    }

}
