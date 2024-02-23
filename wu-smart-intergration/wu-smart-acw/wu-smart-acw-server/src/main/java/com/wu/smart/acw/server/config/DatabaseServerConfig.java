package com.wu.smart.acw.server.config;

import com.wu.smart.acw.server.service.DatabaseServerService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * describe : 服务器信息配置并初始化多数据源
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 19:18
 */
@Configuration
public class DatabaseServerConfig implements InitializingBean {

    private final DatabaseServerService databaseServerService;

    public DatabaseServerConfig(DatabaseServerService databaseServerService) {
        this.databaseServerService = databaseServerService;
    }

    @Override
    public void afterPropertiesSet() {
        databaseServerService.loading();
    }
}
