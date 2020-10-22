package com.wu.framework.shiro.login;

import com.wu.framework.response.Result;
import com.wu.framework.shiro.domain.AccessToken;
import com.wu.framework.shiro.domain.LoginUserBO;
import com.wu.framework.shiro.model.UserDetails;

public interface ILoginService {
    /**
     * @param loginUserBO
     * @return
     */
    Result<AccessToken> accessToken(LoginUserBO loginUserBO);

    /**
     * @param username
     * @param password
     * @param scope
     * @return
     */
    Result<AccessToken> accessToken(String username, String password, String scope);

    /**
     * 用户信息直接加密
     *
     * @param userDetails
     * @param scope
     * @return
     */
    Result<AccessToken> accessToken(UserDetails userDetails, String scope);

    /**
     * 解析令牌
     *
     * @param accessToken
     * @return
     */
    <T> T user(String accessToken);

    /**
     * 移出令牌
     *
     * @param accessToken
     * @return
     */
    Result removeAccessToken(String accessToken);

    /**
     * description 校验令牌
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/10/4 下午4:59
     */
    Boolean checkToken(String accessToken);
}
