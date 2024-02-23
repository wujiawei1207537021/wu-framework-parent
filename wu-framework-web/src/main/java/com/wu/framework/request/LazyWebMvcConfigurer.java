package com.wu.framework.request;

import com.wu.framework.request.method.LazyHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author Jia wei Wu
 */
@Slf4j
public class LazyWebMvcConfigurer implements WebMvcConfigurer {

    private final List<LazyHandlerMethodArgumentResolver> lazyHandlerMethodArgumentResolvers;
    private final List<LazyInterceptorRegistry> lazyInterceptorRegistries;

    public LazyWebMvcConfigurer(List<LazyHandlerMethodArgumentResolver> lazyHandlerMethodArgumentResolvers, List<LazyInterceptorRegistry> lazyInterceptorRegistries) {
        this.lazyHandlerMethodArgumentResolvers = lazyHandlerMethodArgumentResolvers;
        this.lazyInterceptorRegistries = lazyInterceptorRegistries;
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
        for (LazyInterceptorRegistry lazyInterceptorRegistry : lazyInterceptorRegistries) {

            registry.addInterceptor(lazyInterceptorRegistry).
                    addPathPatterns(lazyInterceptorRegistry.addPathPatterns()).
                    excludePathPatterns(lazyInterceptorRegistry.excludePathPatterns());
        }
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
        resolvers.addAll(lazyHandlerMethodArgumentResolvers);
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
