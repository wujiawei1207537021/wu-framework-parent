//package com.wuframework.shiro.domain;
//
//
//
//import java.util.Collection;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//public class WuAuthenticationToken extends AbstractAuthenticationToken {
//    private static final long serialVersionUID = 510L;
//    private LoginUserBO loginUserBO;
//
//    public WuAuthenticationToken(LoginUserBO loginUserBO) {
//        super((Collection)null);
//        this.loginUserBO = loginUserBO;
//        this.setAuthenticated(false);
//    }
//
//    public WuAuthenticationToken(LoginUserBO loginUserBO, Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.loginUserBO = loginUserBO;
//        super.setAuthenticated(true);
//    }
//
//    public Object getCredentials() {
//        return null;
//    }
//
//    public String getPrincipal() {
//        return this.loginUserBO.getUsername();
//    }
//
//    public LoginUserBO getLoginUserBO() {
//        return this.loginUserBO;
//    }
//
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        if (isAuthenticated) {
//            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        } else {
//            super.setAuthenticated(false);
//        }
//    }
//
//    public void eraseCredentials() {
//        super.eraseCredentials();
//    }
//}
