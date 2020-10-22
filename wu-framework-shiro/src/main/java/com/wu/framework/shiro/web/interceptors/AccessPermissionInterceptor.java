package com.wu.framework.shiro.web.interceptors;

import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.shiro.annotation.RequiredRole;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.login.ILoginService;
import com.wu.framework.shiro.login.UserDetailsService;
import com.wu.framework.shiro.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制角色
 */

@Slf4j
public class AccessPermissionInterceptor implements HandlerInterceptor {


    @Resource
    private ShiroProperties shiroProperties;

    @Resource
    private ILoginService iLoginService;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 验证权限
        if (this.hasPermission(request, handler)) {
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


    /**
     * 是否有权限
     */
    private boolean hasPermission(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            // 如果标记了注解，则判断权限
            String accessToken = request.getHeader(shiroProperties.getTokenName());
            if (ObjectUtils.isEmpty(accessToken)) {
                log.error("获取请求头中令牌失败请求地址:==>" + request.getRequestURI());
                return false;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredRole requiredRole = handlerMethod.getMethod().getAnnotation(RequiredRole.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredRole == null) {
                requiredRole = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredRole.class);
            }
            UserDetails userDetails = iLoginService.user(accessToken);
            // 如果注解为null, 说明不需要拦截, 直接放过
            if (requiredRole == null) {
                return userDetails != null;
            }
            //数据库查询
            userDetails = userDetailsService.loadUserByUsername(userDetails.getUsername());
            request.setAttribute("AccessTokenUser", userDetails);
            if (ObjectUtils.isEmpty(userDetails.getRoleSignList())) {
                return false;
            }
            //或的角色
            if (!ObjectUtils.isEmpty(requiredRole.orRoles())) {
                for (String role : requiredRole.orRoles()) {
                    if (userDetails.getRoleSignList().contains(role)) {
                        return true;
                    }
                }
            }
            //必须角色
            if (!ObjectUtils.isEmpty(requiredRole.roles())) {
                for (String role : requiredRole.roles()) {
                    if (userDetails.getRoleSignList().contains(role)) {
                    } else {
                        return false;
                    }
                }
            }
            //放行
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
