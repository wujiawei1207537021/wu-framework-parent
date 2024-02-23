package com.wu.framework.easy.upsert.autoconfigure.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/7/13 9:06 下午
 */
@Data
@Configuration
@ConditionalOnProperty(prefix = SpringBootPulsarConfigProperties.PULSAR_PREFIX, value = "url", matchIfMissing = true)
@ConfigurationProperties(prefix = SpringBootPulsarConfigProperties.PULSAR_PREFIX)
public class SpringBootPulsarConfigProperties {

    public static final String PULSAR_PREFIX = "spring.pulsar";

    private String url = "localhost:6650";
}
