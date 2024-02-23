package com.wu.framework.easy.listener.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author 林濯颜
 * @date 2022/5/9 14:49
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class SpringBootMysqlBinLogConfigProperties {

    /**
     * 默认当前连接地址
     */
    private String url;
    /**
     * 默认当前用户名
     */
    private String username;
    /**
     * 默认当前密码
     */
    private String password;
    /**
     * serverId
     */
    private Long serverId = 65535L;
    /**
     * 是否开启 默认开启
     */
    private boolean enableBinlog = true;
}
