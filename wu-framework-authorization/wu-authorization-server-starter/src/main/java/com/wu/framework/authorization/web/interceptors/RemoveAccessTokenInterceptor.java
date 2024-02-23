package com.wu.framework.authorization.web.interceptors;

import com.wu.framework.authorization.annotation.RemoveAccessToken;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.login.ILoginService;
import com.wu.framework.request.LazyHandlerInterceptor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

@Slf4j
public class RemoveAccessTokenInterceptor implements LazyHandlerInterceptor {

    @Resource
    private ILoginService ILoginService;

    @Resource
    private AuthorizationProperties authorizationProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RemoveAccessToken removeAccessToken = handlerMethod.getMethod().getAnnotation(RemoveAccessToken.class);
            if (ObjectUtils.isEmpty(removeAccessToken)) {
                return;
            }
            String accessToken = request.getHeader(authorizationProperties.getTokenName());
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
