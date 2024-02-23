package com.wu.smart.acw.client.ui;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AcwClientUIWebMvcConfigurer implements WebMvcConfigurer {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始静态资源映射...");
        registry.addResourceHandler("/client-ui/**").addResourceLocations("classpath:/dist/");
    }
}
