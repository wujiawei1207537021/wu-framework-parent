package com.wuframework.shiro.login;

import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.shiro.domain.AccessToken;
import com.wuframework.shiro.domain.LoginUserBO;
import com.wuframework.shiro.exceptions.CustomException;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.shiro.token.TokenStore;
import com.wuframework.shiro.web.configuration.AuthorizationServerEndpointsConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

/**
 * @ Description   :  封装的方法
 * @ Author        :  wujiawei
 * @ CreateDate    :  2019/12/17 0017 11:44
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2019/12/17 0017 11:44
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


@Service
public class LoginServiceImpl implements LoginService {


    /**
     * {@link AuthorizationServerEndpointsConfigurer#getTokenStore()} ()}
     */
    @Deprecated
    private TokenStore tokenStore;

    /**
     * {@link AuthorizationServerEndpointsConfigurer#getUserDetailsService()} ()} ()}
     */
    @Deprecated
    private UserDetailsService userDetailsService;

    private AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer;

    @Autowired
    private LoginServiceImpl(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer, AuthorizationServerEndpointsConfigurer defaultAuthorizationServerEndpointsConfigurer) {

        // 外面注入 AuthorizationServerEndpointsConfigurer  bean
        if (ObjectUtils.isEmpty(authorizationServerEndpointsConfigurer)) {
            if (ObjectUtils.isEmpty(defaultAuthorizationServerEndpointsConfigurer)) {
                throw new CustomException("Failed to find bean of authorizationServerEndpointsConfigurer ");
            } else {
                this.authorizationServerEndpointsConfigurer = defaultAuthorizationServerEndpointsConfigurer;
            }
        } else {
            this.authorizationServerEndpointsConfigurer = authorizationServerEndpointsConfigurer;
        }

//        // 用户登录服务
//        if (ObjectUtils.isEmpty(userDetailsService)) {
//            this.userDetailsService = defaultUserDetailsService;
//        } else {
//            this.userDetailsService = userDetailsService;
//        }
//        // token 机制
//        if (ObjectUtils.isEmpty(tokenStore)) {
//            this.tokenStore = defaultTokenStore;
//        } else {
//            this.tokenStore = tokenStore;
//        }

    }

    /**
     * @param loginUserBO
     * @return
     */
    @Override
    public Result<AccessToken> accessToken(LoginUserBO loginUserBO) {
        return accessToken(loginUserBO.getUsername(), loginUserBO.getPassword(), loginUserBO.getScope());
    }


    @Override
    public Result<AccessToken> accessToken(String username, String password, String scope) {
        UserDetails userDetails = authorizationServerEndpointsConfigurer.getUserDetailsService().loadUserByUsername(username);
        //支持MD5密码校验
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (ObjectUtils.isEmpty(userDetails)) {
//            throw new TokenAuthorizationException("用户不存在");
            return ResultFactory.invalidTokenAuthorizationOf("令牌授权失败(用户不存在)");
        }
        if (userDetails.getPassword().equals(md5Password)) {
            //返回令牌
            return accessToken(userDetails, scope);
        } else {
//            throw new TokenAuthorizationException("令牌授权失败");
            return ResultFactory.invalidTokenAuthorizationOf("令牌授权失败(用户或密码错误)");
        }
    }


    @Override
    public Result<AccessToken> accessToken(UserDetails userDetails ,String scope) {
        return ResultFactory.successOf(authorizationServerEndpointsConfigurer.getTokenStore().getAccessToken(userDetails, scope));
    }

    /**
     * 移出令牌
     *
     * @param accessToken
     * @return
     */
    @Override
    public Result removeAccessToken(String accessToken) {
        authorizationServerEndpointsConfigurer.getTokenStore().removeAccessToken(accessToken);
        return ResultFactory.successOf();
    }

    /**
     * 解析令牌
     *
     * @param accessToken
     * @return
     */
    @Override
    public UserDetails user(String accessToken) {
      return authorizationServerEndpointsConfigurer.getTokenStore().readAccessToken(accessToken, authorizationServerEndpointsConfigurer.getUserDetails());

    }


}
