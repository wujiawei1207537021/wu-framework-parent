package com.wu.framework.shiro.token.store;

import com.wu.framework.shiro.config.pro.ShiroProperties;
import com.wu.framework.shiro.domain.AccessToken;
import com.wu.framework.shiro.domain.DefaultAccessToken;
import com.wu.framework.shiro.domain.DefaultAuthentication;
import com.wu.framework.shiro.exceptions.TokenAuthorizationException;
import com.wu.framework.shiro.model.UserDetails;
import com.wu.framework.shiro.token.TokenStore;
import com.wu.framework.shiro.domain.Authentication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.SerializationUtils;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = "spring.shiro",value ="token-store" ,havingValue = "JDBC_TOKEN_STORE",matchIfMissing = true)
public class JdbcTokenStore implements TokenStore {
    private static final Log LOG = LogFactory.getLog(JdbcTokenStore.class);
    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT =
            "insert into access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String DEFAULT_ACCESS_TOKEN_SELECT_STATEMENT =
            "select token_id, token from access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, authentication from access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, token from access_token where authentication_id = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_AND_CLIENT_SELECT_STATEMENT =
            "select token_id, token from access_token where user_name = ? and client_id = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_USERNAME_SELECT_STATEMENT =
            "select token_id, token from access_token where user_name = ?";
    private static final String DEFAULT_ACCESS_TOKENS_FROM_CLIENTID_SELECT_STATEMENT =
            "select token_id, token from access_token where client_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_STATEMENT =
            "delete from access_token where token_id = ?";
    private static final String DEFAULT_ACCESS_TOKEN_DELETE_FROM_REFRESH_TOKEN_STATEMENT =
            "delete from access_token where refresh_token = ?";
    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT =
            "insert into refresh_token (token_id, token, authentication) values (?, ?, ?)";
    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT =
            "select token_id, token from refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, authentication from refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT =
            "delete from refresh_token where token_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private String insertAccessTokenSql =
            "insert into access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";
    private String updateAccessTokenSql =
            "update access_token set authentication=? where user_name=?";

    private String selectAccessTokenSql =
            "select token_id, token from access_token where token_id = ?";
    private String selectAccessTokenAuthenticationSql =
            "select token_id, authentication from access_token where token_id = ?";
    private String selectAccessTokenFromAuthenticationSql =
            "select token_id, token from access_token where authentication_id = ?";
    private String selectAccessTokensFromUserNameAndClientIdSql =
            "select token_id, token from access_token where user_name = ? and client_id = ?";
    private String selectAccessTokensFromUserNameSql =
            "select token_id, token from access_token where user_name = ?";
    private String selectAccessTokensFromClientIdSql =
            "select token_id, token from access_token where client_id = ?";
    private String deleteAccessTokenSql = "delete from access_token where token_id = ?";
    private String insertRefreshTokenSql =
            "insert into refresh_token (token_id, token, authentication) values (?, ?, ?)";
    private String selectRefreshTokenSql =
            "select token_id, token from refresh_token where token_id = ?";
    private String selectRefreshTokenAuthenticationSql =
            "select token_id, authentication from refresh_token where token_id = ?";
    private String deleteRefreshTokenSql = "delete from refresh_token where token_id = ?";
    private String deleteAccessTokenFromRefreshTokenSql =
            "delete from access_token where refresh_token = ?";

    private final ShiroProperties shiroProperties;
    public JdbcTokenStore(DataSource dataSource, ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public static void main(String[] args) {
//    User user= new User();
//    user.setUsername("jjjj");
//    user.setPassword("xx");
//    JdbcTokenStore jdbcTokenStore=new JdbcTokenStore(null);
//    String ss=jdbcTokenStore.extractTokenKey(user.toString());
//    System.out.println(ss);
//    }

    public AccessToken getAccessToken(String authenticationId) {
        AccessToken accessToken = null;
        String key = "";

        try {
            accessToken =
                    (AccessToken)
                            this.jdbcTemplate.queryForObject(
                                    this.selectAccessTokenFromAuthenticationSql,
                                    new RowMapper<AccessToken>() {
                                        @Override
                                        public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                                            return JdbcTokenStore.this.deserializeAccessToken(rs.getBytes(2));
                                        }
                                    },
                                    new Object[]{authenticationId});
        } catch (EmptyResultDataAccessException var5) {
            if (LOG.isDebugEnabled()) {
            }

        } catch (IllegalArgumentException var6) {
        }

        return accessToken;
    }

    @Override
    public AccessToken convertAccessToken(String var1) {
        return null;
    }

    @Override
    public <T> T readAccessToken(String var1, Class<T> clazz) {
        //TODO  令牌过期问题
        try {
            Authentication authentication = this.jdbcTemplate.queryForObject(
                    this.selectAccessTokenAuthenticationSql,
                    new JdbcTokenStore.SafeAuthenticationRowMapper(),
                    new Object[]{extractTokenKey(var1)});
            if(ObjectUtils.isEmpty(authentication)){
                throw new TokenAuthorizationException("令牌过期");
            }
            return (T) authentication.getUserDetails();
        } catch (EmptyResultDataAccessException exception) {
            throw new TokenAuthorizationException("令牌过期");
        }
    }

    @Override
    public void removeAccessToken(String var1) {
        jdbcTemplate.update(
                JdbcTokenStore.this.deleteAccessTokenSql, new Object[]{extractTokenKey(var1)});
    }

    @Override
    public AccessToken getAccessToken(Authentication var1) {
        return null;
    }

    public void removeRefreshToken(String token) {
        this.jdbcTemplate.update(
                this.deleteRefreshTokenSql, new Object[]{this.extractTokenKey(token)});
    }

    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        this.jdbcTemplate.update(
                this.deleteAccessTokenFromRefreshTokenSql,
                new Object[]{this.extractTokenKey(refreshToken)},
                new int[]{12});
    }

    @Override
    public Collection<AccessToken> findTokensByClientId(String clientId) {
        Object accessTokens1 = new ArrayList();

        try {
            accessTokens1 =
                    this.jdbcTemplate.query(
                            this.selectAccessTokensFromClientIdSql,
                            new JdbcTokenStore.SafeAccessTokenRowMapper(),
                            new Object[]{clientId});
        } catch (EmptyResultDataAccessException var4) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for clientId " + clientId);
            }
        }

        List<AccessToken> accessTokens = this.removeNulls((List) accessTokens1);
        return accessTokens;
    }

    public Collection<AccessToken> findTokensByUserName(String userName) {
        Object accessTokens1 = new ArrayList();

        try {
            accessTokens1 =
                    this.jdbcTemplate.query(
                            this.selectAccessTokensFromUserNameSql,
                            new JdbcTokenStore.SafeAccessTokenRowMapper(),
                            new Object[]{userName});
        } catch (EmptyResultDataAccessException var4) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for userName " + userName);
            }
        }

        List<AccessToken> accessTokens = this.removeNulls((List) accessTokens1);
        return accessTokens;
    }

    @Override
    public Collection<AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Object accessTokens1 = new ArrayList();

        try {
            accessTokens1 =
                    this.jdbcTemplate.query(
                            this.selectAccessTokensFromUserNameAndClientIdSql,
                            new JdbcTokenStore.SafeAccessTokenRowMapper(),
                            new Object[]{userName, clientId});
        } catch (EmptyResultDataAccessException var5) {
            if (LOG.isInfoEnabled()) {
                LOG.info(
                        "Failed to find access token for clientId " + clientId + " and userName " + userName);
            }
        }

        List<AccessToken> accessTokens = this.removeNulls((List) accessTokens1);
        return accessTokens;
    }

    private List<AccessToken> removeNulls(List<AccessToken> accessTokens) {
        List<AccessToken> tokens = new ArrayList();
        Iterator var3 = accessTokens.iterator();

        while (var3.hasNext()) {
            AccessToken token = (AccessToken) var3.next();
            if (token != null) {
                tokens.add(token);
            }
        }

        return tokens;
    }

    @Override
    public AccessToken getAccessToken(UserDetails var1, String scope) {
        //数据加密
        AccessToken accessToken = new DefaultAccessToken();
        Authentication authentication = new DefaultAuthentication();
        authentication.setScope(scope);
        authentication.setUserDetails(var1);
        //查询
//        System.out.println(authentication.tosin());
//        System.out.println(var1.tosin());
//        System.out.println(extractTokenKey(authentication.tosin()));
        try {
            accessToken = this.jdbcTemplate.queryForObject(
                    this.selectAccessTokenFromAuthenticationSql,
                    new JdbcTokenStore.SafeAccessTokenRowMapper(),
                    new Object[]{extractTokenKey(authentication.tosin())});
            if (ObjectUtils.isEmpty(accessToken)) {
                throw new EmptyResultDataAccessException(0);
            }
            if (accessToken.getExpiresDate().before(new Date())) {
                jdbcTemplate.update(
                        JdbcTokenStore.this.deleteAccessTokenSql, new Object[]{extractTokenKey(accessToken.getAccessToken())});
                throw new EmptyResultDataAccessException(0);
            } else {
                accessToken.setExpiresIn(accessToken.getExpiresDate().getTime() - System.currentTimeMillis());
            }

        } catch (EmptyResultDataAccessException var4) {
            LOG.info("Failed to find access token for clientId " + var4);
            //token_id, token, authentication_id, user_name, client_id, authentication, refresh_token
            //加密数据存储
            assert accessToken != null;
            accessToken.setAccessToken(this.extractTokenKey(var1.toString()) + System.currentTimeMillis());
            accessToken.setRefreshToken(this.extractTokenKey(accessToken.getAccessToken()));
            accessToken.setScope(scope);
            jdbcTemplate.update(insertAccessTokenSql, new Object[]{extractTokenKey(accessToken.getAccessToken()),
                    new SqlLobValue(serializeAccessToken(accessToken)), extractTokenKey(authentication.tosin()),
                    var1.getUsername(),
                    null,
                    new SqlLobValue(serializeAuthentication(authentication)), extractTokenKey(accessToken.getRefreshToken())}, new int[]{
                    Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR});
            if (LOG.isInfoEnabled()) {
                LOG.info("Failed to find access token for clientId " + accessToken);
            }
            accessToken.setExpiresIn(shiroProperties.getExpireTime());
        }
        accessToken.setExpiresDate(null);
        return accessToken;
    }

    /**
     * 刷新令牌内信息
     * @param var1
     * @return
     */
    @Override
    public void refreshAccessToken(UserDetails var1) {
        Authentication authentication = new DefaultAuthentication();
        authentication.setScope("web");
        authentication.setUserDetails(var1);
        jdbcTemplate.update(updateAccessTokenSql,new Object[]{new SqlLobValue(serializeAuthentication(authentication)),var1.getUsername()},new int[]{Types.BLOB,Types.VARCHAR});
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

            try {
                byte[] bytes = digest.digest(value.getBytes("UTF-8"));
                return String.format("%032x", new BigInteger(1, bytes));
            } catch (UnsupportedEncodingException var4) {
                throw new IllegalStateException(
                        "UTF-8 encoding not available.  Fatal (should be in the JDK).");
            }
        }
    }

    protected byte[] serializeAccessToken(AccessToken token) {
        token.setExpiresDate(new Date(shiroProperties.getExpireTime() + System.currentTimeMillis()));
        return SerializationUtils.serialize(token);
    }


    protected AccessToken deserializeAccessToken(byte[] token) {
        return (AccessToken) SerializationUtils.deserialize(token);
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

    private final class SafeAccessTokenRowMapper implements RowMapper<AccessToken> {
        private SafeAccessTokenRowMapper() {}

        @Override
        public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return JdbcTokenStore.this.deserializeAccessToken(rs.getBytes(2));
            } catch (IllegalArgumentException var5) {
                String token = rs.getString(1);
                JdbcTokenStore.this.jdbcTemplate.update(
                        JdbcTokenStore.this.deleteAccessTokenSql, new Object[]{token});
                return null;
            }
        }
    }

    private final class SafeAuthenticationRowMapper implements RowMapper<Authentication> {
        private SafeAuthenticationRowMapper() {}

        @Override
        public Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
            try {
                return JdbcTokenStore.this.deserializeAuthentication(rs.getBytes(2));
            } catch (IllegalArgumentException var5) {
                String token = rs.getString(1);
                JdbcTokenStore.this.jdbcTemplate.update(
                        JdbcTokenStore.this.deleteAccessTokenSql, new Object[]{token});
                return null;
            }
        }
    }
}
