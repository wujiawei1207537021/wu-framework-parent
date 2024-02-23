package com.wu.smart.acw.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * description 服务点配置
 *
 * @author 吴佳伟
 * @date 2023/07/17 15:39
 */
@Configuration
@ConfigurationProperties(prefix = AcwServerProperties.PREFIX)
@Data
public class AcwServerProperties {
    public static final String PREFIX = "spring.acw";
    /**
     * 服务端地址
     */
    private String serverUrl = "http://127.0.0.1:18080";

    /**
     * 应用ID
     */
    private String applicationId;
    /**
     * 应用密钥
     */
    private String  secret;

    /**
     * 使用客户端生成的Java代码存放路径
     */
    private String localJavaPath;
    /**
     * 客户端ID 当前客户端自己的ID如果为空默认是ip
     */
    private String clientId;


}
