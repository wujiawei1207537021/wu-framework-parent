package com.wu.framework.shiro.web.configuration;


import com.wu.framework.shiro.login.UserDetailsService;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @ Description   :  默认接口实现类 未注入
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/3/18 0018 8:52
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/3/18 0018 8:52
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */

@ConditionalOnMissingBean(AuthorizationServerEndpointsConfigurer.class)
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
