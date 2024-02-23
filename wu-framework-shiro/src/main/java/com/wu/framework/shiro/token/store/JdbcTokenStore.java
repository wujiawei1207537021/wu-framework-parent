package com.wu.framework.shiro.token.store;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.constant.ShiroConfigConstant;
import com.wu.framework.shiro.domain.AccessTokenRO;
import com.wu.framework.shiro.domain.Authentication;
import com.wu.framework.shiro.domain.DefaultAccessTokenRO;
import com.wu.framework.shiro.domain.DefaultAuthentication;
import com.wu.framework.shiro.model.AccessToken;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.SerializationUtils;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = ShiroConfigConstant.SHIRO_PREFIX, value = "token-store", havingValue = "JDBC_TOKEN_STORE", matchIfMissing = true)
public class JdbcTokenStore implements TokenStore {
    private static final Log LOG = LogFactory.getLog(JdbcTokenStore.class);

    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT =
            "insert into access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (%s, %s, %s, %s, %s, %s, %s)";
    private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT =
            "select token_id, token from access_token where token_id = %s";
    private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, authentication from access_token where token_id = %s";
    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, token from access_token where authentication_id = %s";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT =
            "select token_id, token from access_token where user_name = %s and client_id = %s";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT =
            "select token_id, token from access_token where user_name = %s";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT =
            "select token_id, token from access_token where client_id = %s";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT =
            "delete from access_token where token_id = %s";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT =
            "delete from access_token where refresh_token = %s";
    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT =
            "insert into refresh_token (token_id, token, authentication) values (%s, %s, %s)";
    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT =
            "select token_id, token from refresh_token where token_id = %s";
    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, authentication from refresh_token where token_id = %s";
    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT =
            "delete from refresh_token where token_id = %s";
    private final LazyOperation lazyOperation;
    private final ShiroProperties shiroProperties;
    private String insertAccessTokenSql =
            "insert into access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (%s, %s, %s, %s, %s, %s, %s)";
    private String updateAccessTokenSql =
            "update access_token set authentication=%s where user_name=%s";
    private String selectAccessTokenSql =
            "select token_id, token from access_token where token_id = %s";
    private String selectAccessTokenAuthenticationSql =
            "select token_id, authentication from access_token where token_id = %s";
    private String selectAccessTokenFromAuthenticationSql =
            "select token_id, token from access_token where authentication_id = %s";
    private String selectAccessTokensFromUserNameAndClientIdSql =
            "select token_id, token from access_token where user_name = %s and client_id = %s";
    private String selectAccessTokensFromUserNameSql =
            "select token_id, token from access_token where user_name = %s";
    private String selectAccessTokensFromClientIdSql =
            "select token_id, token from access_token where client_id = %s";
    private String deleteAccessTokenSql = "delete from access_token where token_id = %s";
    private String insertRefreshTokenSql =
            "insert into refresh_token (token_id, token, authentication) values (%s, %s, %s)";
    private String selectRefreshTokenSql =
            "select token_id, token from refresh_token where token_id = %s";
    private String selectRefreshTokenAuthenticationSql =
            "select token_id, authentication from refresh_token where token_id = %s";
    private String deleteRefreshTokenSql = "delete from refresh_token where token_id = %s";
    private String deleteAccessTokenFromRefreshTokenSql =
            "delete from access_token where refresh_token = %s";

    public JdbcTokenStore(LazyOperation lazyOperation, ShiroProperties shiroProperties) {
        this.lazyOperation = lazyOperation;
        this.shiroProperties = shiroProperties;
    }

//    public static void main(String[] args) {
//    User user= new User();
//    user.setUsername("jjjj");
//    user.setPassword("xx");
//    JdbcTokenStore jdbcTokenStore=new JdbcTokenStore(null);
//    String ss=extractTokenKey(user.toString());
//    System.out.println(ss);
//    }

    public AccessTokenRO getAccessToken(String authenticationId) {
        AccessTokenRO accessTokenRO = deserializeAccessToken(
                lazyOperation.executeSQLForBean(String.format(selectAccessTokenFromAuthenticationSql, authenticationId), DefaultAccessTokenRO.class).getAccessToken().getBytes());
        return accessTokenRO;
    }

    @Override
    public AccessTokenRO convertAccessToken(String var1) {
        return null;
    }

    @Override
    public <T> T readAccessToken(String token, Class<T> clazz) {
        //TODO  令牌过期问题
        AccessToken accessToken = lazyOperation.
                executeSQLForBean(String.format("select token_id, authentication from access_token where token_id = '%s'", extractTokenKey(token)), AccessToken.class);
//            Authentication authentication = this.lazyOperation.queryForObject(
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
        lazyOperation.executeSQLForBean(String.format(this.deleteAccessTokenSql, extractTokenKey(var1)), Boolean.class);
    }

    @Override
    public AccessTokenRO getAccessToken(Authentication authentication) {
        return null;
    }

    public void removeRefreshToken(String token) {
        this.lazyOperation.executeSQLForBean(String.format(this.deleteRefreshTokenSql, this.extractTokenKey(token)), Boolean.class);
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        this.lazyOperation.executeSQLForBean(String.format(
                this.deleteAccessTokenFromRefreshTokenSql,
                this.extractTokenKey(refreshToken)),
                Boolean.class);
    }

    @Override
    public Collection<AccessTokenRO> findTokensByClientId(String clientId) {
        Object accessTokens1 = new ArrayList();

        accessTokens1 =
                this.lazyOperation.executeSQLForBean(String.format(
                        this.selectAccessTokensFromClientIdSql,
                        clientId), Boolean.class);
        List accessTokens = this.removeNulls((List) accessTokens1);
        return accessTokens;
    }

    public Collection<AccessTokenRO> findTokensByUserName(String userName) {
        Object
                accessTokens1 =
                this.lazyOperation.executeSQLForBean(String.format(
                        this.selectAccessTokensFromUserNameSql,
                        userName), Object.class);
        List<AccessTokenRO> accessTokenROS = this.removeNulls((List) accessTokens1);
        return accessTokenROS;
    }

    @Override
    public Collection<AccessTokenRO> findTokensByClientIdAndUserName(String clientId, String userName) {
        Object accessTokens1 =
                this.lazyOperation.executeSQLForBean(String.format(
                        this.selectAccessTokensFromUserNameAndClientIdSql,
                        userName, clientId), Boolean.class);
        List accessTokens = this.removeNulls((List) accessTokens1);
        return accessTokens;
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
        AccessTokenRO accessTokenRO = new DefaultAccessTokenRO();
        Authentication authentication = new DefaultAuthentication();
        authentication.setScope(scope);
        authentication.setUserDetails(userDetails);
        //查询
//        System.out.println(authentication.tosin());
//        System.out.println(userDetails.tosin());
//        System.out.println(extractTokenKey(authentication.tosin()));
        AccessToken accessToken = new AccessToken();

        try {
            accessTokenRO = this.lazyOperation.executeSQLForBean(String.format(
                    this.selectAccessTokenFromAuthenticationSql,
                    extractTokenKey(authentication.tosin())), DefaultAccessTokenRO.class);
            if (accessTokenRO.getExpiresDate().before(new Date())) {
                lazyOperation.executeSQLForBean(String.format(
                        this.deleteAccessTokenSql, extractTokenKey(accessTokenRO.getAccessToken())), Boolean.class);
            } else {
                accessTokenRO.setExpiresIn(accessTokenRO.getExpiresDate().getTime() - System.currentTimeMillis());
            }

        } catch (Exception var4) {
            LOG.info("Failed to find access token for clientId " + var4);
            //token_id, token, authentication_id, user_name, client_id, authentication, refresh_token
            //加密数据存储
            assert accessTokenRO != null;
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
            lazyOperation.activeUpsert(accessToken);
            accessTokenRO.setExpiresIn(shiroProperties.getExpireTime());
        }
        accessTokenRO.setExpiresDate(null);
        return accessTokenRO;
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
        lazyOperation.executeSQLForBean(String.format(updateAccessTokenSql, Arrays.toString(serializeAuthentication(authentication)), userDetails.getUsername()), Boolean.class);
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
        token.setExpiresDate(new Date(shiroProperties.getExpireTime() + System.currentTimeMillis()));
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

    public void setInsertAccessTokenSql(String insertAccessTokenSql) {
        this.insertAccessTokenSql = insertAccessTokenSql;
    }

    public void setSelectAccessTokenSql(String selectAccessTokenSql) {
        this.selectAccessTokenSql = selectAccessTokenSql;
    }

    public void setDeleteAccessTokenSql(String deleteAccessTokenSql) {
        this.deleteAccessTokenSql = deleteAccessTokenSql;
    }

    public void setInsertRefreshTokenSql(String insertRefreshTokenSql) {
        this.insertRefreshTokenSql = insertRefreshTokenSql;
    }

    public void setSelectRefreshTokenSql(String selectRefreshTokenSql) {
        this.selectRefreshTokenSql = selectRefreshTokenSql;
    }

    public void setDeleteRefreshTokenSql(String deleteRefreshTokenSql) {
        this.deleteRefreshTokenSql = deleteRefreshTokenSql;
    }

    public void setSelectAccessTokenAuthenticationSql(String selectAccessTokenAuthenticationSql) {
        this.selectAccessTokenAuthenticationSql = selectAccessTokenAuthenticationSql;
    }

    public void setSelectRefreshTokenAuthenticationSql(String selectRefreshTokenAuthenticationSql) {
        this.selectRefreshTokenAuthenticationSql = selectRefreshTokenAuthenticationSql;
    }

    public void setSelectAccessTokenFromAuthenticationSql(
            String selectAccessTokenFromAuthenticationSql) {
        this.selectAccessTokenFromAuthenticationSql = selectAccessTokenFromAuthenticationSql;
    }

    public void setDeleteAccessTokenFromRefreshTokenSql(String deleteAccessTokenFromRefreshTokenSql) {
        this.deleteAccessTokenFromRefreshTokenSql = deleteAccessTokenFromRefreshTokenSql;
    }

    public void setSelectAccessTokensFromUserNameSql(String selectAccessTokensFromUserNameSql) {
        this.selectAccessTokensFromUserNameSql = selectAccessTokensFromUserNameSql;
    }

    public void setSelectAccessTokensFromUserNameAndClientIdSql(
            String selectAccessTokensFromUserNameAndClientIdSql) {
        this.selectAccessTokensFromUserNameAndClientIdSql =
                selectAccessTokensFromUserNameAndClientIdSql;
    }

    public void setSelectAccessTokensFromClientIdSql(String selectAccessTokensFromClientIdSql) {
        this.selectAccessTokensFromClientIdSql = selectAccessTokensFromClientIdSql;
    }


}
