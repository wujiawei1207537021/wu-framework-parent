package com.wu.framework.authorization.web;

import com.wu.framework.authorization.config.AuthorizationCORSConfiguration;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.endpoint.TokenKeyEndpoint;
import com.wu.framework.authorization.login.DefaultUserDetailsService;
import com.wu.framework.authorization.login.LoginService;
import com.wu.framework.authorization.web.interceptors.AccessPermissionInterceptorAbstract;
import com.wu.framework.authorization.web.interceptors.RemoveAccessTokenInterceptor;
import com.wu.framework.authorization.web.interceptors.SessionPermissionInterceptorAbstract;
import com.wu.framework.authorization.web.methodresolver.AccessTokenUserMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(value = {AccessTokenUserMethodArgumentResolver.class, RemoveAccessTokenInterceptor.class, AuthorizationProperties.class,
        AccessPermissionInterceptorAbstract.class, SessionPermissionInterceptorAbstract.class,
        LoginService.class, TokenKeyEndpoint.class, DefaultUserDetailsService.class, AuthorizationCORSConfiguration.class})
public class AuthorizationWebMvcConfigurer {

}
