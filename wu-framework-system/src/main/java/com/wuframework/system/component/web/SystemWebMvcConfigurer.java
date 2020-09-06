package com.wuframework.system.component.web;

import com.wuframework.system.component.web.interceptors.AccessPermissionInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 分页 权限
 */
@Component
public class SystemWebMvcConfigurer implements WebMvcConfigurer {

//    @Bean
//    public AccessPermissionInterceptor accessPermissionInterceptor(){
//        return new AccessPermissionInterceptor();
//    }
    @Resource
    private AccessPermissionInterceptor accessPermissionInterceptor;

//    @Bean
//    public PageMethodArgumentResolver pageMethodArgumentResolver() {
//        return new PageMethodArgumentResolver();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessPermissionInterceptor).addPathPatterns("/**");
    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(pageMethodArgumentResolver());
//    }

}
