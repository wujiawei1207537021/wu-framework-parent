package com.wu.smart.acw.client.nocode.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * description 路径拦截
 *
 * @author 吴佳伟
 * @date 2023/08/11 15:03
 */
public interface PathFilter {

    /**
     * 判断当前请求是否是动态请求
     * @param servletRequest
     * @return
     */
    boolean support(ServletRequest servletRequest);

    /**
     * 处理动态请求
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException;




}
