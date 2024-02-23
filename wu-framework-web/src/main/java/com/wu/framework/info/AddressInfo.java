package com.wu.framework.info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.util.ObjectUtils;

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

    public AddressInfo(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void afterPropertiesSet() throws UnknownHostException {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isEmpty(port)) {
            port = 8080;
        }
        log.info("加载 web-swagger2地址: http://127.0.0.1:{}/swagger-ui.html", port);
        log.info("加载 web-swagger3地址: http://127.0.0.1:{}/swagger-ui/index.html", port);
    }
}
