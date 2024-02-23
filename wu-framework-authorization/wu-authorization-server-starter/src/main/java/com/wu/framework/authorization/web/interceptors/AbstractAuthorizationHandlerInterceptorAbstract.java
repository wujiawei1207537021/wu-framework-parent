package com.wu.framework.authorization.web.interceptors;

import com.wu.framework.authorization.annotation.NoAuthorizationRequired;
import com.wu.framework.authorization.annotation.RequiredRole;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.toolkit.AuthorizationContextHolder;
import com.wu.framework.authorization.util.IpUtil;
import com.wu.framework.authorization.util.ShiroSessionContextUtil;
import com.wu.framework.request.LazyInterceptorRegistry;
import com.wu.framework.response.enmus.DefaultResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;


/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/8 9:50 下午
 */

public abstract class AbstractAuthorizationHandlerInterceptorAbstract implements LazyInterceptorRegistry {

    private final AuthorizationProperties authorizationProperties;
    private AntPathMatcher matcher = new AntPathMatcher();

    protected AbstractAuthorizationHandlerInterceptorAbstract(AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = IpUtil.getIpAddr();
        System.out.printf("访问者地址:%s%n", ipAddr);
        String requestURI = request.getRequestURI();
        // 验证权限
        if (authorizationProperties.getUnCheckApiPath().stream().anyMatch(unCheck -> matcher.match(unCheck, requestURI)) ||
                this.hasPermission(request, handler)) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(DefaultResultCode.UN_AUTHORIZATION.getCode());
        String s = "{\"code\":" + DefaultResultCode.UN_AUTHORIZATION.getCode() + ", \"message\":\"" + DefaultResultCode.UN_AUTHORIZATION.getMessage() + "\"}";
        response.getOutputStream().write(s.getBytes());
        return false;
    }

    /**
     * 验证用户是否有权限
     *
     * @param request 当前请求
     * @param handler 处理对象
     * @return 布尔类型
     */
    boolean hasPermission(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            // 获取方法上的注解
            NoAuthorizationRequired noAuthorizationRequired = handlerMethod.getMethod().getAnnotation(NoAuthorizationRequired.class);
            if (noAuthorizationRequired != null) {
                return true;
            }
            //放行
            return doHasPermission(request, handlerMethod);
        }
        return true;
    }

    /**
     * 验证用户是否有权限
     *
     * @param request 当前请求
     * @param handler 处理对象
     * @return 布尔类型
     */
    abstract boolean doHasPermission(HttpServletRequest request, HandlerMethod handler);

    /**
     * 缓存授权信息
     */
    public void cacheAuthorization(UserDetails userDetails) {
        AuthorizationContextHolder.setCurrentAuthorization(userDetails);
    }

    ;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthorizationContextHolder.clear();
    }
}
