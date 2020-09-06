package com.wuframework.shiro.login;

import com.wuframework.response.Result;
import com.wuframework.shiro.domain.AccessToken;
import com.wuframework.shiro.domain.LoginUserBO;
import com.wuframework.shiro.model.UserDetails;

public interface LoginService {
    /**
     * @param loginUserBO
     * @return
     */
    Result<AccessToken> accessToken(LoginUserBO loginUserBO);

    /**
     *
     * @param username
     * @param password
     * @param scope
     * @return
     */
     Result<AccessToken> accessToken(String username, String password, String scope) ;

    /**
     * 用户信息直接加密
     * @param userDetails
     * @param scope
     * @return
     */
     Result<AccessToken> accessToken(UserDetails userDetails , String scope);
    /**
     * 解析令牌
     *
     * @param accessToken
     * @return
     */
    <T> T user(String accessToken);

    /**
     * 移出令牌
     * @param accessToken
     * @return
     */
    Result removeAccessToken(String accessToken);
}
