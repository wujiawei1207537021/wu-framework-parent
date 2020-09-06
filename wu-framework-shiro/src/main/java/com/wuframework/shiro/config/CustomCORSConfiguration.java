package com.wuframework.shiro.config;

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

@Configuration
public class CustomCORSConfiguration {
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
}

