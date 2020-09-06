package com.wuframework.system.component.web.interceptors;


import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.LoginService;
import com.wuframework.shiro.UserDetailsService;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.shiro.config.pro.ShiroProperties;
import com.wuframework.shiro.exceptions.TokenAuthorizationException;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.system.common.annotation.RequiredPermission;
import com.wuframework.system.common.entity.SysPermission;
import com.wuframework.system.common.enums.PermissionTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 控制权限
 */

@Slf4j
@Component("accessPermissionInterceptor")
public class AccessPermissionInterceptor implements HandlerInterceptor {


    @Resource
    private ShiroProperties shiroProperties;

    @Resource
    private LoginService loginService;

    @Resource
    private UserDetailsService userDetailsService;

    private  AntPathMatcher matcher = new AntPathMatcher();

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
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getMethod().getDeclaringClass();

            // 类上的角色注解
            RequiredRole requiredRole = handlerMethod.getMethod().getAnnotation(RequiredRole.class);
            if (requiredRole == null) {
                requiredRole = clazz.getAnnotation(RequiredRole.class);
            }
            // 角色注解优先
            if(!ObjectUtils.isEmpty(requiredRole)){
                return true;
            }
            // 获取方法上的权限注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = clazz.getAnnotation(RequiredPermission.class);
            }
            // 如果注解为null, 说明不需要拦截, 直接放过
            if (requiredPermission == null) {
                return true;
            }
            // 如果标记了注解，则判断权限
            String accessToken = request.getHeader(shiroProperties.getTokenName());
            if (ObjectUtils.isEmpty(accessToken)) {
                throw new TokenAuthorizationException("获取请求头中令牌失败请求地址:==>" + request.getRequestURI());
            }
            UserDetails userDetails = loginService.user(accessToken);
            userDetails= userDetailsService.loadUserByUsername(userDetails.getUsername());
            request.setAttribute("AccessTokenUser",userDetails);
            if (ObjectUtils.isEmpty(userDetails.getPermissionList())) {
                return false;
            }
            List<SysPermission> permissionList = userDetails.getPermissionList();
//           step1 获取请求路径
            String path = request.getRequestURI();
            System.out.println("获取请求路径"+path);
//            step2 匹配请求路径
            permissionList = permissionList.stream().filter(p -> matcher.match(p.getPermissionCompletePath(),path)).collect(Collectors.toList());
//            step3 匹配请求方法类型
            List<String> permissionTypeList = permissionList.stream().map(p -> p.getPermissionType()).collect(Collectors.toList());
//            当前支持CRUD 四种操作方式
            for (PermissionTypeEnums permissionTypeEnmus : PermissionTypeEnums.values()) {
                if (!ObjectUtils.isEmpty(handlerMethod.getMethod().getAnnotation(permissionTypeEnmus.getAClass()))) {
                    if (permissionTypeList.contains(permissionTypeEnmus.name())) {
                        return true;
                    }
                }
            }
            //权限校验失败不能放行
            return false;
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
