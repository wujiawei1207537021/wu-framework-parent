package com.wu.framework.inner.ftp.util;

/**
 * description
 *
 * @Author Jia wei Wu
 * @Date 2020-05-22 2:30 下午
 */

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


//@Component
public class WifiFileDownLoadRoute extends RouteBuilder {

    @Value("${ftp.wifi-info.server-info}")
    private String wifiDataUrl;

    @Value("${ftp.mobile-info.local-save-dir}")
    private String localDir;

    @Autowired
    private WifiFileProcessor wifiFileProcessor;

    @Override
    public void configure() throws Exception {
        from(wifiDataUrl).to("file:" + localDir).process(wifiFileProcessor);
    }
}
