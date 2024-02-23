package com.wu.framework.info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/6/27 7:55 下午
 */
@Slf4j
public class AddressInfo implements InitializingBean {

    private final ServerProperties serverProperties;
    private final ConfigurableApplicationContext configurableApplicationContext;

    public AddressInfo(ServerProperties serverProperties, ConfigurableApplicationContext configurableApplicationContext) {
        this.serverProperties = serverProperties;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    @Override
    public void afterPropertiesSet() throws UnknownHostException {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isEmpty(port)) {
            port = 8080;
        }
        String contextPath = "";
        ServerProperties.Servlet servlet = serverProperties.getServlet();
        if (servlet != null && null != servlet.getContextPath()) {
            contextPath = servlet.getContextPath();
        }
//        log.info("加载 web-swagger2地址: http://127.0.0.1:{}{}/swagger-ui.html", getPort, contextPath);
//        log.info("加载 web-swagger3地址: http://127.0.0.1:{}{}/swagger-ui/index.html", getPort, contextPath);


        Environment env = configurableApplicationContext.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "Doc: \t\thttp://{}:{}{}/doc.html\n\t" +
                        "Swagger2: \thttp://{}:{}{}/swagger-ui.html\n\t" +
                        "Swagger3: \thttp://{}:{}{}/swagger-ui/index.html\n\t" +
                        "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                contextPath,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                contextPath,
                InetAddress.getLocalHost().getHostAddress(),
                port,
                contextPath
        );
    }
}
