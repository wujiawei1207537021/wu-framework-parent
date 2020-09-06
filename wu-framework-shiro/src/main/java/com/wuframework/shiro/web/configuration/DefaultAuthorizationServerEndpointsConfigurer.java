package com.wuframework.shiro.web.configuration;


import com.wuframework.shiro.login.UserDetailsService;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.shiro.token.TokenStore;
import lombok.Data;
/**
 * @ Description   :  默认接口实现类 未注入
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/3/18 0018 8:52
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/3/18 0018 8:52
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


@Data
public class DefaultAuthorizationServerEndpointsConfigurer implements AuthorizationServerEndpointsConfigurer {

    private TokenStore tokenStore;

    private UserDetailsService userDetailsService;

    private Class<? extends UserDetails> userDetails;

    @Override
    public AuthorizationServerEndpointsConfigurer setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
        return this;
    }

    @Override
    public AuthorizationServerEndpointsConfigurer setUserDetailsService(
            UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        return this;
    }

    @Override
    public AuthorizationServerEndpointsConfigurer setUserDetails(Class<? extends UserDetails> userDetails) {
        this.userDetails = userDetails;
        return this;
    }

}
