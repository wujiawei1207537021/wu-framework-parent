package com.wu.framework.inner.intergration.rocketmq;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnProperty(prefix = RocketProperties.ROCKET_PREFIX, name = "namesrv-addr")
@ConfigurationProperties(prefix = RocketProperties.ROCKET_PREFIX)
public class RocketProperties {


    public static final String ROCKET_PREFIX = "spring.rocket";


    private String namesrvAddr = "127.0.0.1:9876";

    private Integer maxMessageSize = 1024 * 1024 * 4; // 4M;
    private Integer sendMsgTimeout = 3000;
    private Integer retryTimesWhenSendFailed = 2;

    // 消费者 最小线程数
    private Integer consumeThreadMin;
    // 消费者 最大线程数量
    private Integer consumeThreadMax;
    // 批量消费的数据条数
    private Integer consumeMessageBatchMaxSize;


}
