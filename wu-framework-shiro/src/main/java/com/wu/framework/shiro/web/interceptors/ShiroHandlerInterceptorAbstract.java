package com.wu.framework.shiro.web.interceptors;

import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.util.IpUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/1/8 9:50 下午
 */

public abstract class ShiroHandlerInterceptorAbstract implements HandlerInterceptor {

    private final ShiroProperties shiroProperties;
    private AntPathMatcher matcher = new AntPathMatcher();

    protected ShiroHandlerInterceptorAbstract(ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
    }


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtil.getIpAddr();
        System.out.printf("访问者地址:%s%n", ipAddr);
        String requestURI = request.getRequestURI();
        // 验证权限
        if (shiroProperties.getUnCheckApiPath().stream().anyMatch(unCheck -> matcher.match(unCheck, requestURI)) || this.hasPermission(request, handler)) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=UTF-8");
        String s = "{\"code\":" + DefaultResultCode.UN_AUTHORIZATION.getCode() + ", \"message\":\"" + DefaultResultCode.UN_AUTHORIZATION.getMessage() + "\"}";
        response.getOutputStream().write(s.getBytes());
        return false;
    }

    abstract boolean hasPermission(HttpServletRequest request, Object handler);

}
