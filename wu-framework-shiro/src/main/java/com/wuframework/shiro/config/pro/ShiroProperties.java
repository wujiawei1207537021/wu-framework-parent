package com.wuframework.shiro.config.pro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.shiro")
public class ShiroProperties {
    /**
     * token的名称
     */
    private String tokenName = "access_token";
    /**
     * 刷新校验的token字段
     */
    private String refreshToken = "refresh_token";
    /**
     * 加密盐
     */
    private String secretKey = "yuntsoft!@#";
    /**
     * 过期时间2个小时
     */
    private Long expireTime = Long.parseLong("7200000");
    // 2 * 60 * 60 * 1000 7200000
    /**
     * 刷新的token过期时间为7天 7 * 24 * 60 * 60 * 1000;
     */
    private Long refreshTime = Long.parseLong("604800000");
}
