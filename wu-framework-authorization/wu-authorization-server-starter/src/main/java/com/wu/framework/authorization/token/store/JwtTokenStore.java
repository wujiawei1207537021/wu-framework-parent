package com.wu.framework.authorization.token.store;

import com.auth0.jwt.interfaces.Claim;
import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.domain.AccessTokenRO;
import com.wu.framework.authorization.domain.Authentication;
import com.wu.framework.authorization.domain.DefaultAccessTokenRO;
import com.wu.framework.authorization.exceptions.TokenAuthorizationException;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.token.TokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

/**
 * @see AuthorizationProperties.TokenStoreEnum
 */
@ConditionalOnMissingBean(TokenStore.class)
@ConditionalOnProperty(prefix = AuthorizationProperties.AUTHORIZATION_PREFIX, value = "token-store", havingValue = "JWT_TOKEN_STORE")
public class JwtTokenStore implements TokenStore {

    private final Map<String, DefaultAccessTokenRO> accessTokenMap =
            synchronizedMap(new LinkedHashMap<>());

    private final AuthorizationProperties authorizationProperties;
    private final String USER = "user";
    private final String USER_ID = "user_id";
    private final String SCOPE = "scope";

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    public JwtTokenStore(AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
        this.jwtAccessTokenConverter = new DefaultJwtAccessTokenConverter(authorizationProperties);
    }

//    public JwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
//        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
//    }

    @Override
    public <T> T readAccessToken(String token, Class<T> clazz) {
        if (verifyExp(token)) {
            return jwtAccessTokenConverter.readAccessToken(token, clazz);
        }
        throw new TokenAuthorizationException("令牌过期");
    }

    @Override
    public void removeAccessToken(String var1) {
        UserDetails userDetails = jwtAccessTokenConverter.readAccessToken(var1, UserDetails.class);
        // 默认删除web 令牌
        accessTokenMap.remove(userDetails.getUsername() + "_" + "web");
    }

    @Override
    public AccessTokenRO getAccessToken(Authentication authentication) {

        return null;
    }

    @Override
    public AccessTokenRO getAccessToken(UserDetails userDetails, String scope) {
        // 判断是否存在
        DefaultAccessTokenRO defaultAccessToken;
        if (accessTokenMap.containsKey(userDetails.getUsername() + "_" + scope)) {
            defaultAccessToken = accessTokenMap.get(userDetails.getUsername() + "_" + scope);
            // 是否过期
            if (verifyExp(defaultAccessToken) != null) {
                return verifyExp(defaultAccessToken);
            }
            accessTokenMap.remove(userDetails.getUsername() + "_" + scope);
        }
        // 过期创建新的令牌
        defaultAccessToken =
                (DefaultAccessTokenRO) jwtAccessTokenConverter.createAccessToken(userDetails, scope);
        accessTokenMap.put(userDetails.getUsername() + "_" + scope, defaultAccessToken);
        return jwtAccessTokenConverter.createAccessToken(userDetails, scope);
    }

    @Override
    public void refreshAccessToken(UserDetails var1) {
        //TODO 刷新解析数据
    }

    @Override
    public AccessTokenRO convertAccessToken(String tokenValue) {
        return this.jwtAccessTokenConverter.extractAccessToken(
                tokenValue, this.jwtAccessTokenConverter.decode(tokenValue));
    }

    @Override
    public Collection<AccessTokenRO> findTokensByClientIdAndUserName(String var1, String var2) {

        return null;
    }

    @Override
    public Collection<AccessTokenRO> findTokensByClientId(String var1) {
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
     *
     * @param accessToken
     * @return
     */
    private DefaultAccessTokenRO verifyExp(DefaultAccessTokenRO accessToken) {
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
