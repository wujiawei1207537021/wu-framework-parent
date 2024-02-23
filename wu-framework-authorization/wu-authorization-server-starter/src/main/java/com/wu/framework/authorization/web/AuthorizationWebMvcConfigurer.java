package com.wu.framework.authorization.web;

import com.wu.framework.authorization.config.AuthorizationCORSConfiguration;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.endpoint.TokenKeyEndpoint;
import com.wu.framework.authorization.login.DefaultUserDetailsService;
import com.wu.framework.authorization.login.LoginService;
import com.wu.framework.authorization.web.interceptors.AbstractAuthorizationHandlerInterceptorAbstract;
import com.wu.framework.authorization.web.interceptors.AccessPermissionInterceptorAbstract;
import com.wu.framework.authorization.web.interceptors.RemoveAccessTokenInterceptor;
import com.wu.framework.authorization.web.interceptors.SessionPermissionInterceptorAbstract;
import com.wu.framework.authorization.web.methodresolver.AccessTokenUserMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Slf4j
@Import(value = {AccessTokenUserMethodArgumentResolver.class, RemoveAccessTokenInterceptor.class, AuthorizationProperties.class,
        AccessPermissionInterceptorAbstract.class, SessionPermissionInterceptorAbstract.class,
        LoginService.class, TokenKeyEndpoint.class, DefaultUserDetailsService.class, AuthorizationCORSConfiguration.class})
public class AuthorizationWebMvcConfigurer implements WebMvcConfigurer {


    public final AccessTokenUserMethodArgumentResolver accessTokenUserMethodArgumentResolver;

    public final AbstractAuthorizationHandlerInterceptorAbstract abstractAuthorizationHandlerInterceptorAbstract;

    private final RemoveAccessTokenInterceptor removeAccessTokenInterceptor;

    private final AuthorizationProperties authorizationProperties;

    public AuthorizationWebMvcConfigurer(AccessTokenUserMethodArgumentResolver accessTokenUserMethodArgumentResolver, AbstractAuthorizationHandlerInterceptorAbstract abstractAuthorizationHandlerInterceptorAbstract, RemoveAccessTokenInterceptor removeAccessTokenInterceptor, AuthorizationProperties authorizationProperties) {
        this.accessTokenUserMethodArgumentResolver = accessTokenUserMethodArgumentResolver;
        this.abstractAuthorizationHandlerInterceptorAbstract = abstractAuthorizationHandlerInterceptorAbstract;
        this.removeAccessTokenInterceptor = removeAccessTokenInterceptor;
        this.authorizationProperties = authorizationProperties;
    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(abstractAuthorizationHandlerInterceptorAbstract).addPathPatterns("/**").excludePathPatterns(authorizationProperties.getUnCheckApiPath());
        registry.addInterceptor(removeAccessTokenInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("获取用户方法注解添加");
        resolvers.add(accessTokenUserMethodArgumentResolver);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
