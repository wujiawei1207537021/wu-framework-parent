package com.wu.framework.inner.template.proxy.http.config;

import com.wu.framework.inner.template.proxy.http.ProxyRestTemplate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @author : 吴佳伟
 * @version 1.0
 * describe :
 * @date : 2021/5/22 3:56 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.wu.http")
public class ProxyHttpConfig {

    /***
     * 类型 自动、默认
     */
    private HttpProxyType httpProxyType = HttpProxyType.DYNAMIC;

    private String proxy;
    private int port;
    private String socksProxy;
    private int socksPort;


    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());

        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1) + "."

                + (random.nextInt(255) + 1);

    }

    public String getProxy() {
        if (httpProxyType.equals(HttpProxyType.DYNAMIC)) {
            return randIP();
        }
        return proxy;
    }

    public int getPort() {
        if (httpProxyType.equals(HttpProxyType.DYNAMIC)) {
            Random random = new Random(System.currentTimeMillis());
            return random.nextInt(65535) + 1;
        }
        return port;
    }

    @Bean
    ProxyRestTemplate proxyProxyRestTemplate() {
        return new ProxyRestTemplate(this);
    }

    enum HttpProxyType {
        /**
         * 动态
         */
        DYNAMIC,
        /**
         * 固定的
         */
        FIXED;
    }


}
