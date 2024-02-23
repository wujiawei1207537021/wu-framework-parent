package com.wu.framework.authorization.login;

import com.wu.framework.authorization.domain.AccessTokenRO;
import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.response.Result;

public interface ILoginService {
    /**
     * @param loginUserBO
     * @return
     */
    Result<AccessTokenRO> accessToken(LoginUserBO loginUserBO);

    /**
     * @param username
     * @param password
     * @param scope
     * @return
     */
    Result<AccessTokenRO> accessToken(String username, String password, String scope);

    /**
     * 用户信息直接加密
     *
     * @param userDetails
     * @param scope
     * @return
     */
    Result<AccessTokenRO> accessToken(UserDetails userDetails, String scope);

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
     * @author Jia wei Wu
     * @date 2020/10/4 下午4:59
     */
    Boolean checkToken(String accessToken);

    /**
     * 创建用户
     *
     * @param
     * @return
     * @author Jiawei Wu
     * @date 2021/1/6 8:34 下午
     **/
    Result<AccessTokenRO> createUser(LoginUserBO loginUserBO);
}
