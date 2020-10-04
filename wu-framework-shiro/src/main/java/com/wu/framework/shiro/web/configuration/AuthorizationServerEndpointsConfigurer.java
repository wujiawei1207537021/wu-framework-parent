package com.wu.framework.shiro.web.configuration;

import com.wu.framework.shiro.login.UserDetailsService;
import com.wu.framework.shiro.model.User;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;

import javax.validation.constraints.NotNull;

/**
 * @ Description   :  授权服务配置
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/3/18 0018 8:43
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/3/18 0018 8:43
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public interface AuthorizationServerEndpointsConfigurer {

     TokenStore tokenStore = null;

     @NotNull
     UserDetailsService userDetailsService = null;

     Class<? extends UserDetails> userDetails= User.class;

     AuthorizationServerEndpointsConfigurer setTokenStore(TokenStore tokenStore);

     AuthorizationServerEndpointsConfigurer setUserDetailsService(
            UserDetailsService userDetailsService);

     AuthorizationServerEndpointsConfigurer setUserDetails(Class<? extends UserDetails> userDetails);

    default Class<? extends UserDetails> getUserDetails(){
         return userDetails;
    };

    default  TokenStore getTokenStore(){
         return tokenStore;
    }
    default  UserDetailsService getUserDetailsService(){
         return userDetailsService;
    }


}
