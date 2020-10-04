// package com.wuframework.shiro.factory;
//
//
// import com.wuframework.shiro.DefaultUserDetailsServiceImpl;
// import com.wuframework.shiro.login.UserDetailsService;
// import com.wuframework.shiro.model.User;
// import com.wuframework.shiro.token.TokenStore;
// import com.wuframework.shiro.token.store.JwtTokenStore;
// import com.wuframework.shiro.web.configuration.AuthorizationServerEndpointsConfigurer;
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
// import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Component;
//
//
// @Component
// public class AuthorizationServerFactory implements BeanFactoryPostProcessor {
//    private ConfigurableListableBeanFactory beanFactory;
//    private AuthorizationServerEndpointsConfigurer endpointsConfigurer=new
// AuthorizationServerEndpointsConfigurer();
//
//    public void registerResolvableDependency(Class<?> var1, @Nullable Object var2) {
//        beanFactory.registerResolvableDependency(var1, var2);
//    }
//
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws
// BeansException {
//        this.beanFactory=beanFactory;
//
////        endpointsConfigurer.
////                setTokenStore(new JwtTokenStore()).
////                setUserDetailsService(new DefaultUserDetailsServiceImpl())
////                .setUserDetails(User.class);
//
// this.registerResolvableDependency(AuthorizationServerEndpointsConfigurer.class,endpointsConfigurer);
//        this.registerResolvableDependency(TokenStore.class,endpointsConfigurer.getTokenStore());
//
// this.registerResolvableDependency(UserDetailsService.class,endpointsConfigurer.getUserDetailsService());
//    }
//
//
//    public void setEndpointsConfigurer(AuthorizationServerEndpointsConfigurer endpointsConfigurer)
// {
//        this.endpointsConfigurer = endpointsConfigurer;
//    }
// }
//
//
