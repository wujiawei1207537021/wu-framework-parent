package com.wu.framework.request;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.Arrays;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/9 20:38
 */
public interface LazyInterceptorRegistry extends HandlerInterceptor {
    /**
     * @return
     * @see InterceptorRegistration#addPathPatterns(List)
     */
    default List<String> addPathPatterns() {
        return Arrays.asList("/**");
    }

    ;

    /**
     * @return
     * @see InterceptorRegistration#excludePathPatterns(List)
     */
    default List<String> excludePathPatterns() {
        return Arrays.asList();
    }

    ;

}
