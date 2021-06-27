package com.wu.framework.easy.upsert.autoconfigure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * description upsert 数据源配置
 *
 * @author Jia wei Wu
 * @date 2021/5/11 12:22 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SpringUpsertProperties.UPSERT_PROPERTY_PREFIX)
public class SpringUpsertProperties {

    public static final String UPSERT_PROPERTY_PREFIX = "spring.easy.upsert.property";

    // kafka 配置
    private KafkaProperties kafkaProperties;


    @Data
    public static class KafkaProperties {
        private List<String> bootstrapServers;
    }

}
