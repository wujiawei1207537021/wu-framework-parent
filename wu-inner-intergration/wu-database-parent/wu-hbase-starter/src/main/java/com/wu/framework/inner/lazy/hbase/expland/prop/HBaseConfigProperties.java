package com.wu.framework.inner.lazy.hbase.expland.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Hbase配置类
 *
 * @author sixmonth
 * @Date 2019年5月13日
 */
@Data
@ConfigurationProperties(prefix = HBaseConfigProperties.CONF_PREFIX)
public class HBaseConfigProperties {

    public static final String CONF_PREFIX = "spring.hbase";


    /**
     * zookeeper1:2181,zookeeper2:2181
     */
    private String zookeeperQuorum;

}