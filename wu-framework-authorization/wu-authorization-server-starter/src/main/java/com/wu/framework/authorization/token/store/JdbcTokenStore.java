package com.wu.framework.authorization.token.store;

import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.domain.AccessTokenRO;
import com.wu.framework.authorization.domain.Authentication;
import com.wu.framework.authorization.domain.DefaultAccessTokenRO;
import com.wu.framework.authorization.domain.DefaultAuthentication;
import com.wu.framework.authorization.model.AccessToken;
import com.wu.framework.authorization.model.AuthorizationUser;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.token.TokenStore;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.SerializationUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@ConditionalOnMissingBean(TokenStore.class)
@ConditionalOnProperty(prefix = AuthorizationProperties.AUTHORIZATION_PREFIX, value = "token-store", havingValue = "JDBC_TOKEN_STORE", matchIfMissing = true)
public class JdbcTokenStore implements TokenStore {
    private static final Log LOG = LogFactory.getLog(JdbcTokenStore.class);

    private final LazyLambdaStream lazyLambdaStream;
    private final AuthorizationProperties authorizationProperties;

    private String updateAccessTokenSql =
            "update access_token set authentication=%s where user_name=%s";


    public JdbcTokenStore(LazyLambdaStream lazyLambdaStream, AuthorizationProperties authorizationProperties) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.authorizationProperties = authorizationProperties;
    }

//    public static void main(String[] args) {
//    AuthorizationUser user= new AuthorizationUser();
//    user.setUsername("jjjj");
//    user.setPassword("xx");
//    JdbcTokenStore jdbcTokenStore=new JdbcTokenStore(null);
//    String ss=extractTokenKey(user.toString());
//    System.out.println(ss);
//    }


    @Override
    public AccessTokenRO convertAccessToken(String var1) {
        return null;
    }

    @Override
    public <T> T readAccessToken(String token, Class<T> clazz) {
        //TODO  令牌过期问题
        AccessToken accessToken = lazyLambdaStream.selectOne(
                LazyWrappers.<AccessToken>lambdaWrapper()
                        .eq(AccessToken::getTokenId, extractTokenKey(token))
        );
//            Authentication authentication = this.lazySqlOperation.queryForObject(
//                    this.selectAccessTokenAuthenticationSql,
//                    new SafeAuthenticationRowMapper(),
//                    new Object[]{extractTokenKey(token)});
//            if (ObjectUtils.isEmpty(authentication)) {
//                throw new TokenAuthorizationException("令牌过期");
//            }
        final Authentication authentication = deserializeAuthentication(accessToken.getAuthentication());
        return (T) authentication.getUserDetails();

    }

    @Override
    public void removeAccessToken(String var1) {
//        lazyLambdaStream.executeSQLForBean(String.format(this.deleteAccessTokenSql, extractTokenKey(var1)), Boolean.class);
        lazyLambdaStream.delete(LazyWrappers.<AccessToken>lambdaWrapper().eq(AccessToken::getTokenId, extractTokenKey(var1)));
    }

    @Override
    public AccessTokenRO getAccessToken(Authentication authentication) {
        return null;
    }


    @Override
    public Collection<AccessTokenRO> findTokensByClientId(String clientId) {
        List<AccessTokenRO> accessTokenROList = lazyLambdaStream.selectList(LazyWrappers.<AccessToken>lambdaWrapper()
                .eq(AccessToken::getClientId, clientId), AccessTokenRO.class);
        return this.removeNulls(accessTokenROList);
    }

    public Collection<AccessTokenRO> findTokensByUserName(String userName) {
        List<AccessTokenRO> accessTokenROList = lazyLambdaStream.selectList(LazyWrappers.<AccessToken>lambdaWrapper()
                .eq(AccessToken::getUserName, userName), AccessTokenRO.class);
        return this.removeNulls(accessTokenROList);
    }

    @Override
    public Collection<AccessTokenRO> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<AccessTokenRO> accessTokenROList = lazyLambdaStream.selectList(LazyWrappers.<AccessToken>lambdaWrapper()
                        .eq(AccessToken::getUserName, userName)
                        .eq(AccessToken::getClientId, clientId)
                , AccessTokenRO.class);
        return this.removeNulls(accessTokenROList);
    }

    private List<AccessTokenRO> removeNulls(List<AccessTokenRO> accessTokenROS) {
        List<AccessTokenRO> tokens = new ArrayList();

        for (AccessTokenRO token : accessTokenROS) {
            if (token != null) {
                tokens.add(token);
            }
        }

        return tokens;
    }

    @Override
    public AccessTokenRO getAccessToken(UserDetails userDetails, String scope) {
        //数据加密

        Authentication authentication = new DefaultAuthentication();
        authentication.setScope(scope);
        authentication.setUserDetails(userDetails);
        //查询
//        System.out.println(authentication.tosin());
//        System.out.println(userDetails.tosin());
//        System.out.println(extractTokenKey(authentication.tosin()));
        AccessToken accessToken = new AccessToken();

        try {
            AccessTokenRO accessTokenRO = lazyLambdaStream.selectOne(LazyWrappers
                            .<AccessToken>lambdaWrapper()
                            .eq(AccessToken::getAuthenticationId, extractTokenKey(authentication.tosin())),
                    DefaultAccessTokenRO.class
            );
            if (accessTokenRO.getExpiresDate().before(new Date())) {
                lazyLambdaStream
                        .delete(LazyWrappers.<AccessToken>lambdaWrapper()
                                .eq(AccessToken::getTokenId, extractTokenKey(accessTokenRO.getAccessToken()))
                        );
            } else {
                accessTokenRO.setExpiresIn(accessTokenRO.getExpiresDate().getTime() - System.currentTimeMillis());
            }
            return accessTokenRO;

        } catch (Exception var4) {
            LOG.info("Failed to find access token for clientId " + var4);
            //token_id, token, authentication_id, user_name, client_id, authentication, refresh_token
            //加密数据存储
            AccessTokenRO accessTokenRO = new DefaultAccessTokenRO();
            accessTokenRO.setAccessToken(this.extractTokenKey(userDetails.toString()) + System.currentTimeMillis());
            accessTokenRO.setRefreshToken(this.extractTokenKey(accessTokenRO.getAccessToken()));
            accessTokenRO.setScope(scope);

            // token_id, token, authentication_id, user_name, client_id, authentication, refresh_token
            accessToken.setTokenId(extractTokenKey(accessTokenRO.getAccessToken())).setToken(serializeAccessToken(accessTokenRO))
                    .setAuthenticationId(extractTokenKey(authentication.tosin()))
                    .setUserName(userDetails.getUsername())
                    .setClientId(null)
                    .setAuthentication(serializeAuthentication(authentication))
                    .setRefreshToken(extractTokenKey(accessTokenRO.getRefreshToken()));
            lazyLambdaStream.upsert(accessToken);
            accessTokenRO.setExpiresIn(authorizationProperties.getExpireTime());
            accessTokenRO.setExpiresDate(null);
            return accessTokenRO;
        }
    }

    /**
     * 刷新令牌内信息
     *
     * @param userDetails
     * @return
     */
    @Override
    public void refreshAccessToken(UserDetails userDetails) {
        Authentication authentication = new DefaultAuthentication();
        authentication.setScope("web");
        authentication.setUserDetails(userDetails);
        lazyLambdaStream.executeSQLForBean(String.format(updateAccessTokenSql, Arrays.toString(serializeAuthentication(authentication)), userDetails.getUsername()), Boolean.class);
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        } else {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var5) {
                throw new IllegalStateException(
                        "MD5 algorithm not available.  Fatal (should be in the JDK).");
            }

            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return String.format("%032x", new BigInteger(1, bytes));
        }
    }

    protected byte[] serializeAccessToken(AccessTokenRO token) {
        token.setExpiresDate(new Date(authorizationProperties.getExpireTime() + System.currentTimeMillis()));
        return SerializationUtils.serialize(token);
    }


    protected AccessTokenRO deserializeAccessToken(byte[] token) {
        return (AccessTokenRO) SerializationUtils.deserialize(token);
    }

    protected byte[] serializeAuthentication(Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected Authentication deserializeAuthentication(byte[] token) {
        return (Authentication) SerializationUtils.deserialize(token);
    }


}
