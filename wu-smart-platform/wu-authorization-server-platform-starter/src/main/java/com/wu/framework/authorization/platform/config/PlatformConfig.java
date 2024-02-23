package com.wu.framework.authorization.platform.config;

import com.wu.framework.authorization.config.pro.AuthorizationProperties;
import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.login.UserDetailsService;
import com.wu.framework.inner.lazy.stereotype.LazyScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;

/**
 * describe : 平台配置
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 17:54
 */
@Slf4j
@ComponentScan(basePackages = "com.wu.framework.authorization.platform")
@LazyScan(scanBasePackages = "com.wu.framework.authorization.platform.infrastructure.entity")
public class PlatformConfig implements InitializingBean {

    private final UserDetailsService userDetailsService;
    private final AuthorizationProperties authorizationProperties;

    public PlatformConfig(UserDetailsService userDetailsService, AuthorizationProperties authorizationProperties) {
        this.userDetailsService = userDetailsService;
        this.authorizationProperties = authorizationProperties;
    }

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化账号
        log.info("初始化账号: {}/{} ", authorizationProperties.getUserName(), authorizationProperties.getPassword());
        try {
            userDetailsService.createUser(new LoginUserBO().setUsername(authorizationProperties.getUserName()).setPassword(authorizationProperties.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
