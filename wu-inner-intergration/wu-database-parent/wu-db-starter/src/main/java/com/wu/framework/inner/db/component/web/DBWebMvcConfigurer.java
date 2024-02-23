package com.wu.framework.inner.db.component.web;


import com.wu.framework.inner.db.component.web.methodresolver.JpaPageRequestMethodArgumentResolver;
import com.wu.framework.inner.db.component.web.methodresolver.PageMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 分页
 */
public class DBWebMvcConfigurer implements WebMvcConfigurer {


    @Bean
    public PageMethodArgumentResolver pageMethodArgumentResolver() {
        return new PageMethodArgumentResolver();
    }


    @Bean
    public JpaPageRequestMethodArgumentResolver jpaPageRequestMethodArgumentResolver() {
        return new JpaPageRequestMethodArgumentResolver();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageMethodArgumentResolver());
        resolvers.add(jpaPageRequestMethodArgumentResolver());
    }
}
