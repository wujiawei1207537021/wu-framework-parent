package com.wu.framework.authorization.config;

import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.login.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ Description   :  跨域请求的配置类
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/7 0007 9:43
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/7 0007 9:43
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */
@Slf4j
public class AuthorizationCORSConfiguration implements InitializingBean {


    private final UserDetailsService userDetailsService;
    private final AuthorizationProperties authorizationProperties;

    public AuthorizationCORSConfiguration(UserDetailsService userDetailsService, AuthorizationProperties authorizationProperties) {
        this.userDetailsService = userDetailsService;
        this.authorizationProperties = authorizationProperties;
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 允许任何的head头部
        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedMethod("*"); // 允许任何的请求方法
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    // 添加CorsFilter拦截器，对任意的请求使用
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化账号
        log.info("初始化账号: {}/{} ", authorizationProperties.getUserName(), authorizationProperties.getPassword());
        userDetailsService.createUser(new LoginUserBO().setUsername(authorizationProperties.getUserName()).setPassword(authorizationProperties.getPassword()));
    }

    @ConditionalOnMissingBean(PasswordEncoder.class)
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

