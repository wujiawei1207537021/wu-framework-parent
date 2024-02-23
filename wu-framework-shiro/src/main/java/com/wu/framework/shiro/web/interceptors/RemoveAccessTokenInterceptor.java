package com.wu.framework.shiro.web.interceptors;

import com.wu.framework.shiro.annotation.RemoveAccessToken;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.login.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RemoveAccessTokenInterceptor implements HandlerInterceptor {

    @Resource
    private ILoginService ILoginService;

    @Resource
    private ShiroProperties shiroProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RemoveAccessToken removeAccessToken = handlerMethod.getMethod().getAnnotation(RemoveAccessToken.class);
            if (ObjectUtils.isEmpty(removeAccessToken)) {
                return;
            }
            String accessToken = request.getHeader(shiroProperties.getTokenName());
            //成功 removeAccessToken.success()&!ObjectUtils.isEmpty(ex)
            // 失败移出 !removeAccessToken.success()&ObjectUtils.isEmpty(ex)
            boolean xx = removeAccessToken.success() & !ObjectUtils.isEmpty(ex) | !removeAccessToken.success() & ObjectUtils.isEmpty(ex);
            boolean always = removeAccessToken.always() | xx;
            // 总是移出
            if (always) {
                ILoginService.removeAccessToken(accessToken);
                log.info("移出令牌->{}", accessToken);
            }


        }


    }
}
