package com.wu.framework.inner.template.https.config;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpLogging;

/**
 * @Description 认证信息
 * @Author 吴佳伟
 * @Date 2020-05-26 9:05 上午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "https.authorization")
public class HttpsAuthorization implements InitializingBean {

    protected final Log logger = HttpLogging.forLogName(this.getClass());

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 平台名称
     */
    private String platformName;

    public String getBaseAuthorization() {
        if (username == null || password == null) {
            return null;
        }
        return "Basic " + Base64.encodeBase64String((username + ":" + password).getBytes());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化平台：" + platformName);

    }
}
