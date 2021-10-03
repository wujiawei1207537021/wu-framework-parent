package com.wu.framework.easy.upsert.autoconfigure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SpringBootElasticsearchConfigProperties.ELASTICSEARCH_PROPERTIES_PREFIX)
public class SpringBootElasticsearchConfigProperties {
    public static final String ELASTICSEARCH_PROPERTIES_PREFIX = "spring.elasticsearch.rest";

    private List<String> uris = new ArrayList(Collections.singletonList("http://localhost:9200"));
    private String username;
    private String password;
    private Duration connectionTimeout = Duration.ofSeconds(1L);
    private Duration readTimeout = Duration.ofSeconds(30L);
}
