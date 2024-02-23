package com.wu.framework.authorization.config.pro;


import com.wu.framework.authorization.enums.TokenStoreEnum;
import com.wu.framework.authorization.enums.Verification;
import com.wu.framework.authorization.model.AuthorizationUser;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.token.store.JdbcTokenStore;
import com.wu.framework.authorization.token.store.JwtTokenStore;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

@Data
@Configuration
@Import(value = {JdbcTokenStore.class, JwtTokenStore.class})
@ConfigurationProperties(prefix = AuthorizationProperties.AUTHORIZATION_PREFIX)
public class AuthorizationProperties {

    // 前缀
    public static final String AUTHORIZATION_PREFIX = "spring.lazy.authorization";
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
    private String secretKey = "easy.com";
    /**
     * 过期时间2个小时
     */
    private Long expireTime = Long.parseLong("7200000");
    // 2 * 60 * 60 * 1000 7200000
    /**
     * 刷新的token过期时间为7天 7 * 24 * 60 * 60 * 1000;
     */
    private Long refreshTime = Long.parseLong("604800000");

    /**
     * 令牌模式
     */
    private TokenStoreEnum tokenStore = TokenStoreEnum.JDBC_TOKEN_STORE;
    /**
     * 校验方式
     */
    private Verification verification = Verification.TOKEN;

    /**
     * 返回实体格式
     */
    private Class<? extends UserDetails> userDetails = AuthorizationUser.class;

    /**
     * 是否对请求的api进行校验
     */
    private Boolean checkApi = true;

    /**
     * root 账号
     */
    private String userName = "admin";
    /**
     * root 密码
     */
    private String password = "admin";

    /**
     * 忽略的路径
     */
    private List<String> unCheckApiPath = Arrays.asList(
            "/token/**",
            "/error/**",
            "/swagger-ui.html/**",
            "/favicon.ico",
            "/swagger-resources/**",
            "/webjars/**",
            "/v3/api-docs/**"
    );

    public void setUnCheckApiPath(List<String> unCheckApiPath) {
        unCheckApiPath.addAll(this.unCheckApiPath);
        this.unCheckApiPath = unCheckApiPath;
    }


}
