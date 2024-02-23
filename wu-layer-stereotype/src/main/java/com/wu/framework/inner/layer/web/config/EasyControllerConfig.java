package com.wu.framework.inner.layer.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * EasyControllerConfig 配置
 */
@ConfigurationProperties(prefix = EasyControllerConfig.PREFIX)
public class EasyControllerConfig {

    public static final String PREFIX = "spring.easy";

    /**
     * 打印执行时间
     */
    private boolean printExecuteTime = false;


    public boolean isPrintExecuteTime() {
        return printExecuteTime;
    }

    public void setPrintExecuteTime(boolean printExecuteTime) {
        this.printExecuteTime = printExecuteTime;
    }
}
