package com.wu.framework.easy.stereotype.upsert.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 吴佳伟
 * @description
 * @company
 * @date 2021/8/16$ 5:17 下午$
 */
@Data
@ConfigurationProperties(prefix =ElasticsearchProperties.ELASTICSEARCH_PROPERTIES_PREFIX )
public class ElasticsearchProperties {
    public static final String ELASTICSEARCH_PROPERTIES_PREFIX="spring.elasticsearch.rest";

    private List<String> uris = new ArrayList(Collections.singletonList("http://localhost:9200"));
    private String username;
    private String password;
    private Duration connectionTimeout = Duration.ofSeconds(1L);
    private Duration readTimeout = Duration.ofSeconds(30L);
}
