package com.wu.framework.inner.layer.web.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Role;


/**
 * EasyControllerConfig 配置
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConfigurationProperties(prefix = EasyControllerConfig.PREFIX)
public class EasyControllerConfig {

    public static final String PREFIX = "spring.easy";

    /**
     * 打印执行时间
     */
    private boolean printExecuteTime = false;

    /**
     * 打印参数
     */
    private boolean printParams = false;


    public boolean isPrintExecuteTime() {
        return printExecuteTime;
    }

    public void setPrintExecuteTime(boolean printExecuteTime) {
        this.printExecuteTime = printExecuteTime;
    }

    public boolean isPrintParams() {
        return printParams;
    }

    public void setPrintParams(boolean printParams) {
        this.printParams = printParams;
    }
}
