package com.wu.framework.shiro.config;

import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.domain.LoginUserBO;
import com.wu.framework.shiro.login.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class ShiroCORSConfiguration implements InitializingBean {


    public ShiroCORSConfiguration(UserDetailsService userDetailsService, ShiroProperties shiroProperties) {
        this.userDetailsService = userDetailsService;
        this.shiroProperties = shiroProperties;
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


    private final UserDetailsService userDetailsService;
    private final ShiroProperties shiroProperties;


    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化账号
        log.info("初始化账号: {}/{} ",shiroProperties.getUserName(),shiroProperties.getPassword());
        userDetailsService.createUser(new LoginUserBO().setUsername(shiroProperties.getUserName()).setPassword(shiroProperties.getPassword()));
    }
}

