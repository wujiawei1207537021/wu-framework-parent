package com.wu.framework.shiro.login;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.domain.AccessToken;
import com.wu.framework.shiro.domain.LoginUserBO;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;
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

public class LoginService implements ILoginService {

    private final TokenStore tokenStore;

    private final UserDetailsService userDetailsService;

    private final ShiroProperties shiroProperties;

    public LoginService(TokenStore tokenStore, UserDetailsService userDetailsService, ShiroProperties shiroProperties) {
        this.tokenStore = tokenStore;
        this.userDetailsService = userDetailsService;
        this.shiroProperties = shiroProperties;
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
    public Result accessToken(String username, String password, String scope) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
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
    public Result<AccessToken> accessToken(UserDetails userDetails, String scope) {
        return ResultFactory.successOf(tokenStore.getAccessToken(userDetails, scope));
    }

    /**
     * 移出令牌
     *
     * @param accessToken
     * @return
     */
    @Override
    public Result removeAccessToken(String accessToken) {
        tokenStore.removeAccessToken(accessToken);
        return ResultFactory.successOf();
    }

    /**
     * description 校验令牌
     *
     * @param accessToken@return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/10/4 下午4:59
     */
    @Override
    public Boolean checkToken(String accessToken) {
        UserDetails userDetails = user(accessToken);
        return userDetails != null;
    }

    /**
     * 解析令牌
     *
     * @param accessToken
     * @return
     */
    @Override
    public UserDetails user(String accessToken) {
        return tokenStore.readAccessToken(accessToken, shiroProperties.getUserDetails());
    }


}
