package com.wu.framework.easy.pulsar.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConditionalOnProperty(prefix = PulsarConfigProperties.PULSAR_PREFIX, value = "url", matchIfMissing = true)
@ConfigurationProperties(prefix = PulsarConfigProperties.PULSAR_PREFIX)
public class PulsarConfigProperties {

    public static final String PULSAR_PREFIX = "spring.pulsar";

    private String url = "localhost:6650";


}
