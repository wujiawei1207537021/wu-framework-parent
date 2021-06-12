package com.wu.framework.authorization.token;

import com.wu.framework.authorization.domain.AccessTokenRO;
import com.wu.framework.authorization.domain.Authentication;
import com.wu.framework.authorization.model.UserDetails;

import java.util.Collection;

/**
 * @ Description   :  令牌商店
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/14 0014 9:24
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/14 0014 9:24
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public interface TokenStore {

    AccessTokenRO convertAccessToken(String var1);

    /**
     * 解析令牌
     *
     * @param token token
     * @param clazz 返回实体类型
     * @param <T>
     * @return
     */
    <T> T readAccessToken(String token, Class<T> clazz);

    void removeAccessToken(String var1);

    AccessTokenRO getAccessToken(Authentication authentication);

    /**
     * 获取令牌
     *
     * @param userDetails 用户信息
     * @param scope       范围(web app phone )
     * @return
     */
    AccessTokenRO getAccessToken(UserDetails userDetails, String scope);

    /**
     * 刷新令牌内信息
     *
     * @param var1
     * @return
     */
    void refreshAccessToken(UserDetails var1);

    Collection<AccessTokenRO> findTokensByClientIdAndUserName(String var1, String var2);

    Collection<AccessTokenRO> findTokensByClientId(String var1);
}
