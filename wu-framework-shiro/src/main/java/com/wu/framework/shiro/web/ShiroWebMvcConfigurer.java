package com.wu.framework.shiro.web;

import com.wu.framework.shiro.web.interceptors.AccessRoleInterceptor;
import com.wu.framework.shiro.web.interceptors.RemoveAccessTokenInterceptor;
import com.wu.framework.shiro.web.methodresolver.AccessTokenUserMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration("shiroWebMvcConfigurer")
public class ShiroWebMvcConfigurer implements WebMvcConfigurer {

    @Bean
    public AccessTokenUserMethodArgumentResolver accessTokenUserMethodArgumentResolver() {
        return new AccessTokenUserMethodArgumentResolver();
    }

    @Bean
    public AccessRoleInterceptor accessInterceptor() {
        // 权限校验拦截器配置
        return new AccessRoleInterceptor();
    }
    @Resource
    private RemoveAccessTokenInterceptor removeAccessTokenInterceptor;


//    @Resource
//    private AccessLimitInterceptor accessLimitInterceptor;


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {}

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {}

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {}

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {}

    @Override
    public void addFormatters(FormatterRegistry registry) {}


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(removeAccessTokenInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {}

    @Override
    public void addCorsMappings(CorsRegistry registry) {}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {}

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {}

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("获取用户方法注解添加");
        resolvers.add(accessTokenUserMethodArgumentResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {}

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {}

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {}

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
