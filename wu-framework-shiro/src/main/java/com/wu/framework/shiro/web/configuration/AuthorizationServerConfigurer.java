//package com.wu.framework.shiro.web.configuration;
//
//import com.wu.framework.shiro.model.User;
//import com.wu.framework.shiro.model.UserDetails;
//import com.wu.framework.shiro.token.store.JwtTokenStore;
//import org.springframework.stereotype.Component;
//
///**
// * 待完善
// */
//@Deprecated
//@Component
//@EnableAuthorizationServer
//public class AuthorizationServerConfigurer implements AuthorizationServerConfigurerAdapter {
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer)
//            throws Exception {
//        endpointsConfigurer
//                .setTokenStore(new JwtTokenStore())
//                .setUserDetailsService(null)
//                .setUserDetails(User.class);
//    }
//
//    @Override
//    public void configure(UserDetails var1) throws Exception {}
//}
