package com.wu.framework.easy.upsert.autoconfigure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * description kafka 数据源配置
 *
 * @author Jia wei Wu
 * @date 2021/5/11 12:22 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SpringBootKafkaConfigProperties.UPSERT_PROPERTY_PREFIX)
public class SpringBootKafkaConfigProperties {

    public static final String UPSERT_PROPERTY_PREFIX = "spring.kafka";

    // kafka 配置
    private List<String> bootstrapServers;

}
