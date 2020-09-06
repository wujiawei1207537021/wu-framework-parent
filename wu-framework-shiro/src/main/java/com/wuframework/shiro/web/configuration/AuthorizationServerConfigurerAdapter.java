package com.wuframework.shiro.web.configuration;

import com.wuframework.shiro.model.UserDetails;

/**
 * 待完善
 */
@Deprecated
public interface AuthorizationServerConfigurerAdapter {

    void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception;

    void configure(UserDetails userDetails) throws Exception;
}
