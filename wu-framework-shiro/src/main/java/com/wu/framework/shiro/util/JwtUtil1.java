// package com.yuntsoft.cloud.iot.shiro.util;
//
//
// import com.auth0.jwt.JWT;
// import com.auth0.jwt.JWTVerifier;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.exceptions.JWTDecodeException;
// import com.auth0.jwt.interfaces.DecodedJWT;
// import com.yuntsoft.cloud.iot.shiro.config.ShiroPro;
// import com.yuntsoft.cloud.iot.shiro.model.UserDetails;
//
// import java.io.UnsupportedEncodingException;
// import java.util.Date;
//
/// **
// * Json Web Token 校验类
// *
// * @author maohuanhuan
// */
// public class JwtUtil1 {
//
//    public static ShiroPro shiroPro = new ShiroPro();
//
//    /**
//     * 校验token是否正确
//     *
//     * @param token    秘钥
//     * @param username 用户名
//     * @return
//     */
//    public static boolean verify(String token, String username) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("username", username)
//                    .build();
//            DecodedJWT jwt = verifier.verify(token);
//            return true;
//        } catch (Exception exception) {
//            return false;
//        }
//    }
//
//    /**
//     * 根据用户编号校验token是否有效
//     *
//     * @param token  秘钥
//     * @param userId 用户编号
//     * @return
//     */
//    public static boolean verify(String token, Long userId) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("userId", userId)
//                    .build();
//            DecodedJWT jwt = verifier.verify(token);
//            return true;
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 获得token中的信息无需secret解密也能获得
//     *
//     * @param token token
//     * @return
//     */
//    public static String getUsername(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("username").asString();
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 解析令牌 并获取redis键值
//     *
//     * @param accessToken
//     * @return
//     */
//    public static String getAccessTokenRedisKey(String accessToken) {
//        try {
//            DecodedJWT jwt = JWT.decode(accessToken);
//            String userName = jwt.getClaim("username").asString();
//            String scope = jwt.getClaim("scope").asString();
//            return userName + "_" + scope;
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 根据token解析用户信息
//     *
//     * @param accessToken
//     * @return
//     */
//    public static UserDetails getSysUser(String accessToken) {
//        try {
//            DecodedJWT jwt = JWT.decode(accessToken);
//            return jwt.getClaim("sysUser").as(UserDetails.class);
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 获得token中的userId
//     *
//     * @param token
//     * @return
//     */
//    public static Long getUserId(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("userId").asLong();
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 判断是否刷新票据
//     *
//     * @param token
//     * @return
//     */
//    public static boolean getRefreshToken(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("refreshToken").asBoolean();
//        } catch (JWTDecodeException e) {
//            return false;
//        }
//    }
//
//    /**
//     * 生成token加入username和userId
//     *
//     * @param username 用户名称
//     * @param userId   用户编号
//     * @return
//     */
//    public static String sign(String username, Integer userId,String scope) {
//        try {
//            Date date = new Date(System.currentTimeMillis() + shiroPro.getRefreshTime());
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            // 附带username信息
//            return JWT.create()
//                    .withClaim("username", username)
//                    .withClaim("userId", userId)
//                    .withClaim("scope",scope)
//                    .withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            return null;
//        }
//    }
//
//    @Deprecated
//    public static String sign(SysUser sysUser,String scope) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            // 附带sysUser信息
//            return JWT.create()
//                    .withClaim("sysUser", JSONObject.toJSONString(sysUser))
//                    .withClaim("scope",scope)
//                    .sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 创建令牌
//     * @param sysUser
//     * @return
//     */
//    public static DefaultAccessTokenBO defaultAccessToken(SysUser sysUser, String scope) {
//        DefaultAccessTokenBO defaultAccessTokenBO = new DefaultAccessTokenBO();
//        defaultAccessTokenBO.setAccessToken(sign(sysUser.getUsername(),
// sysUser.getUserId(),scope));
//        defaultAccessTokenBO.setExpiresIn(shiroPro.getExpireTime());
//        defaultAccessTokenBO.setScope(scope);
//        defaultAccessTokenBO.setRefreshToken(signRefreshToken(
// sysUser.getUsername(),sysUser.getUserId(),scope));
//        return defaultAccessTokenBO;
//    }
//
//    /**
//     * 生成刷新机制的token
//     *
//     * @param userId   用户编号
//     * @param userName 用户名称
//     * @return
//     */
//    public static String signRefreshToken( String userName,Integer userId,String scope) {
//        try {
//            Date date = new Date(System.currentTimeMillis() + shiroPro.getRefreshTime());
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            // 附带username信息
//            return JWT.create()
//                    .withClaim("userId", userId)
//                    .withClaim("username", userName)
//                    .withClaim("refreshToken", true)
//                    .withClaim("scope",scope)
//                    .withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            return null;
//        }
//    }
//
//    @Deprecated
//    public static String signRefreshToken(SysUser sysUser) {
//        try {
//            Date date = new Date(System.currentTimeMillis() + shiroPro.getRefreshTime());
//            Algorithm algorithm = Algorithm.HMAC256(shiroPro.getSecretKey());
//            // 附带username信息
//            return JWT.create()
//                    .withClaim("sysUser", JSONObject.toJSONString(sysUser))
//                    .withClaim("refreshToken", true)
//                    .withClaim("scope","web")
//                    .withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (UnsupportedEncodingException e) {
//            return null;
//        }
//    }
// }
