package com.wu.framework.shiro.token.store;

import com.wu.framework.shiro.domain.AccessToken;
import com.wu.framework.shiro.model.UserDetails;

import java.util.Map;

public interface JwtAccessTokenConverter {

    /**
     * 提取访问令牌
     *
     * @param value
     * @param map
     * @return
     */
    AccessToken extractAccessToken(String value, Map<String, ?> map);

    /**
     * 创建访问令牌
     *
     * @param var1
     * @param scope
     * @return
     */
    AccessToken createAccessToken(UserDetails var1, String scope);

    /**
     * 令牌解码
     *
     * @param tokenValue
     * @return
     */
    Map<String, ?> decode(String tokenValue);

    /**
     * 令牌读取数据
     *
     * @param var1
     * @return
     */
    <T> T readAccessToken(String var1, Class<T> clazz);
}
