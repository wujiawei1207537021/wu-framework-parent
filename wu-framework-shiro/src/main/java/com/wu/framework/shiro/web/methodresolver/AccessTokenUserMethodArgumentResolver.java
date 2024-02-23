package com.wu.framework.shiro.web.methodresolver;


import com.wu.framework.shiro.annotation.AccessTokenUser;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.exceptions.TokenAuthorizationException;
import com.wu.framework.shiro.login.ILoginService;
import com.wu.framework.shiro.model.UserDetails;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 当前用户参数数据获取类
 * @author: wangq
 * @create: 2019-10-30 21:15
 */
@Component
public class AccessTokenUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private ShiroProperties shiroProperties;

    @Resource
    private ILoginService ILoginService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return UserDetails.class.isAssignableFrom(methodParameter.getParameterType())
                && methodParameter.hasParameterAnnotation(AccessTokenUser.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            @Nullable ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            @Nullable WebDataBinderFactory webDataBinderFactory) throws TokenAuthorizationException {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        Object userDetailsObj = request.getAttribute("AccessTokenUser");

        if (!ObjectUtils.isEmpty(userDetailsObj)) {
            if (userDetailsObj instanceof UserDetails) {
//                System.out.println("请求头中获取数据");
                return (UserDetails) userDetailsObj;
            }
        }
        String accessToken = request.getHeader(shiroProperties.getTokenName());
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new TokenAuthorizationException("获取请求头中令牌失败请求地址:==>" + request.getRequestURI());
        }
        System.out.println("令牌" + accessToken);
        return ILoginService.user(accessToken);
    }
}
