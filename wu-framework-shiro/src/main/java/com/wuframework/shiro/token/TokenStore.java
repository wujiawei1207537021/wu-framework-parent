package com.wuframework.shiro.token;

import com.wuframework.shiro.domain.AccessToken;
import com.wuframework.shiro.domain.Authentication;
import com.wuframework.shiro.model.UserDetails;

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

    AccessToken convertAccessToken(String var1);

    /**
     * 解析令牌
     *
     * @param var1  token
     * @param clazz 返回实体类型
     * @param <T>
     * @return
     */
    <T> T readAccessToken(String var1, Class<T> clazz);

    void removeAccessToken(String var1);

    AccessToken getAccessToken(Authentication var1);

    /**
     * 获取令牌
     *
     * @param var1  用户信息
     * @param scope 范围(web app phone )
     * @return
     */
    AccessToken getAccessToken(UserDetails var1, String scope);

    /**
     * 刷新令牌内信息
     * @param var1
     * @return
     */
    void refreshAccessToken(UserDetails var1);

    Collection<AccessToken> findTokensByClientIdAndUserName(String var1, String var2);

    Collection<AccessToken> findTokensByClientId(String var1);
}
