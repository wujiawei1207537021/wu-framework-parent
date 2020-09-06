package com.wuframework.shiro.token.store;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wuframework.shiro.config.pro.ShiroProperties;
import com.wuframework.shiro.domain.AccessToken;
import com.wuframework.shiro.domain.DefaultAccessToken;
import com.wuframework.shiro.exceptions.ExtractScopeException;
import com.wuframework.shiro.exceptions.TokenAuthorizationException;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.shiro.util.ShiroContextUtil;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public class DefaultJwtAccessTokenConverter implements JwtAccessTokenConverter {


  private final  JSONObject jsonObject=new JSONObject();
    private final String USER = "user";
    private final String USER_ID = "user_id";
    private final String SCOPE = "scope";
    private final String ACCESS_TOKEN = "access_token";
    private String clientIdAttribute = "client_id";


    public DefaultJwtAccessTokenConverter() {
    }

    @Deprecated
    public DefaultJwtAccessTokenConverter(ShiroProperties shiroProperties) {

    }

//    public static void main(String[] args) throws JsonProcessingException {
//        DefaultJwtAccessTokenConverter defaultJwtAccessTokenConverter =
//                new DefaultJwtAccessTokenConverter(new ShiroProperties());
//        String ss = defaultJwtAccessTokenConverter.createRefreshToken("123456789");
//        Map<String, ?> map = defaultJwtAccessTokenConverter.decode(ss);
//        User userDetails = new User();
//        userDetails.setUsername("123");
//        ss = defaultJwtAccessTokenConverter.creatToken(userDetails, "web");
//
//        Map<String, ?> map1 = defaultJwtAccessTokenConverter.decode(ss); // 1576586959365
//
//        System.out.println(defaultJwtAccessTokenConverter.decryptNameForT(ss, "user", User.class));
//
//        System.out.println(((Claim) map1.get("user")).as(User.class));
//    }

    /**
     * 获取配置信息
     *
     * @return
     */
    public static ShiroProperties getShiroProperties() {
        ShiroProperties shiroProperties = (ShiroProperties) ShiroContextUtil.getBean("shiroProperties");
        if (ObjectUtils.isEmpty(shiroProperties)) {
            shiroProperties = new ShiroProperties();
        }
        return shiroProperties;
    }

    @Override
    public AccessToken extractAccessToken(String value, Map<String, ?> map) {
        DefaultAccessToken defaultAccessToken = new DefaultAccessToken();
        defaultAccessToken.setAccessToken(value);
        defaultAccessToken.setScope(this.extractScope(map));
        if (map.containsKey("exp")) {
            defaultAccessToken.setExpiresIn(
                    ((Claim) map.get("exp")).asDate().getTime() - System.currentTimeMillis());
        }
        defaultAccessToken.setRefreshToken(createRefreshToken(value));
        return defaultAccessToken;
    }

    @Override
    public AccessToken createAccessToken(UserDetails var1, String scope) {
        DefaultAccessToken defaultAccessToken = new DefaultAccessToken();
        String accessToken = creatToken(var1, scope);
        defaultAccessToken.setAccessToken(accessToken);
        defaultAccessToken.setScope(scope);
        defaultAccessToken.setExpiresIn(getShiroProperties().getExpireTime());
        defaultAccessToken.setRefreshToken(createRefreshToken(accessToken));
        return defaultAccessToken;
    }

    //    private Set<String> extractScope(Map<String, ?> map) {
    //        Set<String> scope = Collections.emptySet();
    //        if (map.containsKey(this.scopeAttribute)) {
    //            Object scopeObj = map.get(this.scopeAttribute);
    //            if (String.class.isInstance(scopeObj)) {
    //                scope = new
    // LinkedHashSet(Arrays.asList(((String)String.class.cast(scopeObj)).split(" ")));
    //            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
    //                Collection<String> scopeColl = (Collection)scopeObj;
    //                scope = new LinkedHashSet(scopeColl);
    //            }
    //        }
    //
    //        return (Set)scope;
    //    }

    @Override
    public Map<String, ?> decode(String tokenValue) {
        DecodedJWT jwt = JWT.decode(tokenValue);
        return jwt.getClaims();
    }

    @Override
    public <T> T readAccessToken(String var1, Class<T> clazz) {
        return decryptNameForT(var1, USER, clazz);
    }

    private String extractScope(Map<String, ?> map) {
        if (map.containsKey(this.SCOPE)) {
            String scope = (String) map.get(this.SCOPE);
            return scope;
        } else {
            throw new ExtractScopeException("解析令牌scope失败");
        }
    }

    /**
     * 解密accessToken中 过期时间
     *
     * @param accessToken
     * @return
     */
    public Long decryptExpires(String accessToken) {
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            return jwt.getExpiresAt().getTime() - System.currentTimeMillis();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @param accessToken
     * @return
     */
    public String createRefreshToken(String accessToken) {
        try {
            Date date = new Date(System.currentTimeMillis() + getShiroProperties().getRefreshTime());
            Algorithm algorithm = Algorithm.HMAC256(getShiroProperties().getSecretKey());
            // 附带username信息
            return JWT.create().withClaim(ACCESS_TOKEN, accessToken).withExpiresAt(date).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new TokenAuthorizationException("刷新令牌授权异常");
        }
    }

    /**
     * 生成令牌
     *
     * @param userDetails
     * @param scope
     * @return
     */
    public String creatToken(UserDetails userDetails, String scope) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(getShiroProperties().getSecretKey());
            // 附带sysUser信息
            return JWT.create()
                    .withClaim(USER, JSON.toJSONString(userDetails))
                    .withClaim(SCOPE, scope)
                    .withExpiresAt(new Date(System.currentTimeMillis() + getShiroProperties().getExpireTime()))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException  e) {
            throw new TokenAuthorizationException("令牌授权异常");
        }
    }

    /**
     * 解密accessToken中 数据返回泛型
     *
     * @param accessToken
     * @return
     */
    public <T> T decryptNameForT(String accessToken, String name, Class<T> clzz) {
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            System.out.println(jwt.getClaim(name).asString());
            return JSONObject.parseObject(jwt.getClaim(name).asString(), clzz);
            //            return jwt.getClaim(name).as(clzz);
        } catch (JWTDecodeException  e) {
            throw new TokenAuthorizationException("解析令牌失败");
        }
    }
}
