package com.wu.framework.database.generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/4/27 1:51 下午
 */
@Configuration
public class ServerEndpointExporterConfig {

    //    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
