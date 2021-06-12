package com.wu.framework.authorization.web.interceptors;

import com.wu.framework.authorization.annotation.RequiredRole;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.login.ILoginService;
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
@ConditionalOnProperty(prefix = AuthorizationProperties.AUTHORIZATION_PREFIX, name = "verification", havingValue = "TOKEN", matchIfMissing = true)
public class AccessPermissionInterceptor extends AuthorizationHandlerInterceptorAbstract {


    private final AuthorizationProperties authorizationProperties;

    private final ILoginService iLoginService;


    private final UserDetailsService userDetailsService;

    public AccessPermissionInterceptor(AuthorizationProperties authorizationProperties, ILoginService iLoginService, UserDetailsService userDetailsService) {
        super(authorizationProperties);
        this.authorizationProperties = authorizationProperties;
        this.iLoginService = iLoginService;
        this.userDetailsService = userDetailsService;
    }


    /**
     * 是否有权限
     */
    public boolean hasPermission(HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            // 如果标记了注解，则判断权限
            final HttpSession session = request.getSession(true);

            final Object attribute = session.getAttribute(ShiroSessionContextUtil.SESSION_USER_ID);
            String accessToken = request.getHeader(authorizationProperties.getTokenName());
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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
