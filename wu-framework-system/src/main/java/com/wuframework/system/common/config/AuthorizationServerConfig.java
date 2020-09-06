//package com.wuframework.system.common.config;
//
//import com.wuframework.EnableAuthorizationServer;
//import com.wuframework.shiro.token.TokenStore;
//import com.wuframework.shiro.token.store.JdbcTokenStore;
//import com.wuframework.shiro.web.configuration.AuthorizationServerEndpointsConfigurer;
//import com.wuframework.system.common.entity.jpa.DefaultSysUser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//@Component
//@EnableAuthorizationServer
//public class AuthorizationServerConfig {
//
//
//    @Resource
//    private DataSource dataSource;
//
//    @Bean
//    private AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer() {
//        AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer =
//                new AuthorizationServerEndpointsConfigurer();
//        authorizationServerEndpointsConfigurer
//                .setTokenStore(tokenStore())
//                .setUserDetails(SysUser.class);
//        return authorizationServerEndpointsConfigurer;
//    }
//
//    @Bean
//    private TokenStore tokenStore() {
////    return new JwtTokenStore(defaultJwtAccessTokenConverter);
//        return new JdbcTokenStore(dataSource);
//    }
//}
