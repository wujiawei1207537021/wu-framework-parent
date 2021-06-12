package com.wu.framework.authorization.web.interceptors;

import com.wu.framework.authorization.annotation.RequiredRole;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.login.UserDetailsService;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.util.ShiroSessionContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 控制角色
 */

@Slf4j
@ConditionalOnProperty(prefix = AuthorizationProperties.AUTHORIZATION_PREFIX, name = "verification", havingValue = "SESSION")
public class SessionPermissionInterceptor extends AuthorizationHandlerInterceptorAbstract {


    private final AuthorizationProperties authorizationProperties;
    private final UserDetailsService userDetailsService;

    public SessionPermissionInterceptor(AuthorizationProperties authorizationProperties, UserDetailsService userDetailsService) {
        super(authorizationProperties);
        this.authorizationProperties = authorizationProperties;
        this.userDetailsService = userDetailsService;
    }


    /**
     * 是否有权限
     */
    public boolean hasPermission(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            // 如果标记了注解，则判断权限
            HttpSession session = request.getSession(false);
            if (null == session) {
                log.error("获取请求头中令牌失败请求地址:==>" + request.getRequestURI());
                return false;
            }
            Object userId = session.getAttribute(ShiroSessionContextUtil.SESSION_USER_ID);

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredRole requiredRole = handlerMethod.getMethod().getAnnotation(RequiredRole.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredRole == null) {
                requiredRole = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredRole.class);
            }
            UserDetails userDetails = userDetailsService.loadUserById(String.valueOf(userId));
            // 如果注解为null, 说明不需要拦截, 直接放过
            if (requiredRole == null) {
                return userDetails != null;
            }
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
