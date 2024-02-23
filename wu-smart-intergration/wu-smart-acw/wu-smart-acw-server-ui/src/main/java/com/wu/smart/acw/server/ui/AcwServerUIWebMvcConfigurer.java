

package com.wu.smart.acw.server.ui;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;

/**
 * The type Swagger web mvc configurer.
 *
 * @author bnasslahsen
 */

public class AcwServerUIWebMvcConfigurer implements WebMvcConfigurer, InitializingBean {


    private final ServerProperties serverProperties;
    private final ConfigurableApplicationContext configurableApplicationContext;

    // 基础URL路径
    public static final String UI_URL = "wu-smart-acw-ui";

    public AcwServerUIWebMvcConfigurer(ServerProperties serverProperties, ConfigurableApplicationContext configurableApplicationContext) {
        this.serverProperties = serverProperties;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * 设置静态资源映射
     *
     * @param registry 资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始静态资源映射...");
        registry.addResourceHandler("/" + UI_URL + "/**").addResourceLocations("classpath:/acw-server-ui/v1/");

    }

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isEmpty(port)) {
            port = 8080;
        }
        System.out.printf(
                "\n----------------------------------------------------------\n\t" +
                        "Application 'acw-server-ui' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:%s/" + UI_URL + "/index.html\n\t" +
                        "External: \thttp://%s:%s/" + UI_URL + "/index.html\n\t" +
                        "\n----------------------------------------------------------\n",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port
        );
    }
}
