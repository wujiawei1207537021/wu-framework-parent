package com.wuframework.system.common.config;

import com.wuframework.shiro.UserDetailsService;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.shiro.token.TokenStore;
import com.wuframework.shiro.web.configuration.AuthorizationServerEndpointsConfigurer;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * 默认实现注入容器
 */
@Data
@Service("defaultAuthorizationServerEndpointsConfigurer")
public class DefaultAuthorizationServerEndpointsConfigurer implements AuthorizationServerEndpointsConfigurer {

    private TokenStore tokenStore;

    private UserDetailsService userDetailsService;

    private Class<? extends UserDetails> userDetails= DefaultSysUserDetails.class;

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
