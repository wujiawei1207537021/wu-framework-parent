package com.wu.smart.acw.client.listener;


import com.wu.smart.acw.client.properties.AcwServerProperties;
import com.wu.smart.acw.core.server.api.AcwServerClientInstanceApi;
import com.wu.smart.acw.core.server.api.command.AcwClientInstanceAPICommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 注册 客户端到服务端
 */
@Slf4j
public class RegisterClientToServer implements InitializingBean {
    private final ScheduledExecutorService executorService;
    private final AcwServerClientInstanceApi acwServerClientInstanceApi;

    private final ServerProperties serverProperties;
    private final AcwServerProperties acwServerProperties;


    public RegisterClientToServer(AcwServerClientInstanceApi acwServerClientInstanceApi, ServerProperties serverProperties, AcwServerProperties acwServerProperties) {
        this.acwServerClientInstanceApi = acwServerClientInstanceApi;
        this.serverProperties = serverProperties;
        this.acwServerProperties = acwServerProperties;

        this.executorService = new ScheduledThreadPoolExecutor(2, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("com.wu.acw.naming.getList");
            return thread;
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.schedule(new RegisterClientToServerTask(), 30000, TimeUnit.MILLISECONDS);
    }

    class RegisterClientToServerTask implements Runnable {
        @Override
        public void run() {
            try {
                // 业务处理
                // 获取当前地址信息
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                Integer port = serverProperties.getPort();
                if (ObjectUtils.isEmpty(port)) {
                    port = 8080;
                }
                String contextPath = "";
                ServerProperties.Servlet servlet = serverProperties.getServlet();
                if (servlet != null && null != servlet.getContextPath()) {
                    contextPath = servlet.getContextPath();
                }
                String clientId = acwServerProperties.getClientId();
                if (clientId == null) {
                    clientId = hostAddress;
                }
                AcwClientInstanceAPICommand acwClientInstanceAPICommand = new AcwClientInstanceAPICommand();
                acwClientInstanceAPICommand.setIp(hostAddress);
                acwClientInstanceAPICommand.setPort(port);
                acwClientInstanceAPICommand.setPath(contextPath);
                acwClientInstanceAPICommand.setClientId(clientId);
                acwServerClientInstanceApi.story(acwClientInstanceAPICommand);
            } catch (Exception ex) {
                log.error("acw客户端注册服务端异常，异常原因: {}", ex.getMessage(), ex);
            } finally {
                executorService.schedule(new RegisterClientToServerTask(), 30000, TimeUnit.MILLISECONDS);
            }
        }
    }
}
