//package com.wuframework.shiro.web;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.wuframework.shiro.domain.LoginUserBO;
//import com.wuframework.shiro.domain.WuAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.util.StringUtils;
//
//public class xx extends AbstractAuthenticationProcessingFilter {
//    public static final String LOGIN_URL = "/platform/login";
//    private static final String METHOD = "POST";
//    public static final String CAPTCHA = "captcha";
//    public static final String DYNAMIC_CAPTCHA = "dynamicCaptcha";
//    private final ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy;
//
//    public xx(ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy) {
//        super(new AntPathRequestMatcher("/platform/login", "POST"));
//        this.concurrentSessionControlAuthenticationStrategy = concurrentSessionControlAuthenticationStrategy;
//    }
//
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if (!"POST".equals(request.getMethod())) {
//            throw new RuntimeException("登录身份认证接口只支持POST方法！");
//        } else {
//            String contentType = request.getContentType();
//            if (!StringUtils.isEmpty(contentType)) {
//                contentType = contentType.replaceAll("\\s*", "");
//            }
//            LoginUserBO loginUserBO ;
//            if (!"application/json;charset=UTF-8".equalsIgnoreCase(contentType) && !"application/json".equalsIgnoreCase(contentType)) {
//                loginUserBO = new LoginUserBO();
//                loginUserBO.setUsername(request.getParameter("username"));
//                loginUserBO.setPassword(request.getParameter("password"));
////                loginUserBO.setCaptcha(request.getParameter("captcha"));
////                loginUserBO.setDynamicCaptcha(request.getParameter("dynamicCaptcha"));
//            } else {
//                try {
//                    InputStream is = request.getInputStream();
//                    Throwable var6 = null;
//
//                    try {
//                        ObjectMapper mapper = new ObjectMapper();
//                       String a= new String(is.readAllBytes(), StandardCharsets.UTF_8);
////                         a = IOUtils.toString(is, StandardCharsets.UTF_8);
//                        loginUserBO = (LoginUserBO)mapper.readValue(a, LoginUserBO.class);
//                    } catch (Throwable var17) {
//                        var6 = var17;
//                        throw var17;
//                    } finally {
//                        if (is != null) {
//                            if (var6 != null) {
//                                try {
//                                    is.close();
//                                } catch (Throwable var16) {
//                                    var6.addSuppressed(var16);
//                                }
//                            } else {
//                                is.close();
//                            }
//                        }
//
//                    }
//                } catch (IOException var19) {
//                    this.logger.error(var19.getMessage());
//                    throw new RuntimeException("登录身份认证JSON格式数据解析失败！");
//                }
//            }
//
//            if (StringUtils.isEmpty(loginUserBO.getUsername())) {
//                throw new RuntimeException("用户名/手机号码/邮箱不能为空！");
//            } else if (StringUtils.isEmpty(loginUserBO.getPassword())) {
//                throw new RuntimeException("密码不能为空！");
//            } else {
//                this.setSessionAuthenticationStrategy(this.setExceptionIfMaximumExceeded(this.concurrentSessionControlAuthenticationStrategy));
//                WuAuthenticationToken authRequest = new WuAuthenticationToken(loginUserBO);
//                authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//                return this.getAuthenticationManager().authenticate(authRequest);
//            }
//        }
//    }
//
//    private ConcurrentSessionControlAuthenticationStrategy setExceptionIfMaximumExceeded(ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy) {
//        concurrentSessionControlAuthenticationStrategy.setMaximumSessions(-1);
//        concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
////        SystemConfigPO singleLimit = systemConfigService.getOneByCode("_platform_userlogin_singlelimit");
////        if (singleLimit != null && singleLimit.getIsOpen().equals(1)) {
//            boolean flag = true;
////            if (singleLimit.getConfParam().equals(0)) {
////                flag = false;
////            }
//
//            concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(flag);
//            concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
//            return concurrentSessionControlAuthenticationStrategy;
////        } else {
////            return concurrentSessionControlAuthenticationStrategy;
////        }
//    }
//}
