package com.wu.framework.database.generator.config;

import com.wu.framework.database.generator.utils.DateUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Date;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/7/17 下午11:52
 */
@Slf4j
@Data
@Configuration
@ComponentScan(basePackages = "com.wu.framework.database.generator")
@ConfigurationProperties(prefix = "wu.framework.generator")
public class GeneratorConfig implements InitializingBean {
    private String mainPath = "com.wu";
    private String packageName = "com.wu";
    private String moduleName = "module";
    private String author = "wujiawei";
    private String email;
    private String tablePrefix;
    private String dateTime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);

    // 服务配置信息
    private final ServerProperties serverProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化加载 generator-starter 成功 登陆地址: http://127.0.0.1:{}/login.html",serverProperties.getPort());
    }
}
