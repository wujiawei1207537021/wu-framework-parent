package com.wu.framework.shiro.token.store;

import com.auth0.jwt.interfaces.Claim;
import com.wu.framework.shiro.domain.AccessToken;
import com.wu.framework.shiro.domain.DefaultAccessToken;
import com.wu.framework.shiro.exceptions.TokenAuthorizationException;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;
import com.wu.framework.shiro.domain.Authentication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

@ConditionalOnMissingBean(JdbcTokenStore.class)
public class JwtTokenStore implements TokenStore {

    private final Map<String, DefaultAccessToken> accessTokenMap =
            synchronizedMap(new LinkedHashMap<>());

    private final String USER = "user";
    private final String USER_ID = "user_id";
    private final String SCOPE = "scope";

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    public JwtTokenStore() {
        this.jwtAccessTokenConverter = new DefaultJwtAccessTokenConverter();
    }

    public JwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public <T> T readAccessToken(String var1, Class<T> clazz) {
        if (verifyExp(var1)) {
            return jwtAccessTokenConverter.readAccessToken(var1, clazz);
        }
        throw new TokenAuthorizationException("令牌过期");
    }

    @Override
    public void removeAccessToken(String var1) {
        UserDetails userDetails=  jwtAccessTokenConverter.readAccessToken(var1, UserDetails.class);
        // 默认删除web 令牌
        accessTokenMap.remove(userDetails.getUsername()+"_"+"web");
    }

    @Override
    public AccessToken getAccessToken(Authentication var1) {

        return null;
    }

    @Override
    public AccessToken getAccessToken(UserDetails var1, String scope) {
        // 判断是否存在
        DefaultAccessToken defaultAccessToken;
        if (accessTokenMap.containsKey(var1.getUsername() + "_" + scope)) {
            defaultAccessToken = accessTokenMap.get(var1.getUsername() + "_" + scope);
            // 是否过期
            if (verifyExp(defaultAccessToken) != null) {
                return verifyExp(defaultAccessToken);
            }
            accessTokenMap.remove(var1.getUsername() + "_" + scope);
        }
        // 过期创建新的令牌
        defaultAccessToken =
                (DefaultAccessToken) jwtAccessTokenConverter.createAccessToken(var1, scope);
        accessTokenMap.put(var1.getUsername() + "_" + scope, defaultAccessToken);
        return jwtAccessTokenConverter.createAccessToken(var1, scope);
    }

    @Override
    public void refreshAccessToken(UserDetails var1) {
        //TODO 刷新解析数据
    }

    @Override
    public AccessToken convertAccessToken(String tokenValue) {
        return this.jwtAccessTokenConverter.extractAccessToken(
                tokenValue, this.jwtAccessTokenConverter.decode(tokenValue));
    }

    @Override
    public Collection<AccessToken> findTokensByClientIdAndUserName(String var1, String var2) {

        return null;
    }

    @Override
    public Collection<AccessToken> findTokensByClientId(String var1) {
        return null;
    }

    private boolean verifyExp(String accessToken) {
        Map<String, Claim> claimMap = (Map<String, Claim>) jwtAccessTokenConverter.decode(accessToken);
        Long a = claimMap.get("exp").asDate().getTime();
        if (a > System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    /**
     * 校验token是否过期
     * @param accessToken
     * @return
     */
    private DefaultAccessToken verifyExp(DefaultAccessToken accessToken) {
        Map<String, Claim> claimMap =
                (Map<String, Claim>) jwtAccessTokenConverter.decode(accessToken.getAccessToken());

        Long a = claimMap.get("exp").asDate().getTime();
        if (a > System.currentTimeMillis()) {
            accessToken.setExpiresIn(a - System.currentTimeMillis());
            return accessToken;
        }
        return null;
    }
}
