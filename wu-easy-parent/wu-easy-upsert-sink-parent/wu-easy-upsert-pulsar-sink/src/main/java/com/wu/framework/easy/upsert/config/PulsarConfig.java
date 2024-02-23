package com.wu.framework.easy.upsert.config;

import com.wu.framework.easy.upsert.autoconfigure.config.SpringBootPulsarConfigProperties;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnBean(SpringBootPulsarConfigProperties.class)
public class PulsarConfig {

    @Bean
    public PulsarClient pulsarClient(SpringBootPulsarConfigProperties springBootPulsarConfigProperties) throws PulsarClientException {
        return PulsarClient.builder().serviceUrl("pulsar://" + springBootPulsarConfigProperties.getUrl()).build();
    }
}
