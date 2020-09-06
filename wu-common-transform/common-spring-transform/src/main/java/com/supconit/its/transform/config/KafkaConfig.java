package com.supconit.its.transform.config;


import com.google.common.collect.Maps;
import com.supconit.its.transform.config.DataProcessConfig;
import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import com.supconit.its.transform.entity.kafka.TargetJsonSchema;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author 黄珺
 * @date 2019/8/30
 **/
@EnableKafka
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties properties;
    @Autowired
    private DataProcessConfig dataProcessConfig;

    @PostConstruct
    private void init() {
        if (StringUtils.isEmpty(dataProcessConfig.getTaskName())) {
            throw new IllegalArgumentException("请填写转换对应的kafka-connect任务名称");
        }
        List<String> items = Arrays.asList(dataProcessConfig.getBootstrapServers().split(","));
        this.properties.setBootstrapServers(items);
        this.properties.getProducer().setRetries(3);
        this.properties.getProducer().setValueSerializer(KafkaJsonSerializer.class);

        this.properties.getConsumer().setEnableAutoCommit(false);
        this.properties.getListener().setAckMode(ContainerProperties.AckMode.MANUAL);

        //初始化消息的目标schema
        KafkaJsonMessage.defaultSchema = dataProcessConfig.getSchema().get(0);
        KafkaJsonMessage.targetSchemaMap = Maps.uniqueIndex(dataProcessConfig.getSchema(), TargetJsonSchema::getName);
    }

    @Bean
    public ConsumerFactory<?, ?> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                this.properties.buildConsumerProperties());
    }

    @Bean
    public ProducerFactory<String, KafkaJsonMessage> kafkaProducerFactory() {
        DefaultKafkaProducerFactory<String, KafkaJsonMessage> factory = new DefaultKafkaProducerFactory<>(
                this.properties.buildProducerProperties());
        String transactionIdPrefix = this.properties.getProducer()
                .getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }

    @Bean
    public KafkaTemplate<String, KafkaJsonMessage> transformKafkaTemplate() {
        KafkaTemplate<String, KafkaJsonMessage> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory());
        kafkaTemplate.setProducerListener(new LoggingProducerListener<>());
//        kafkaTemplate.setDefaultTopic(ElasticLogMessage.LOG_TOPIC_NAME);
        return kafkaTemplate;
    }
}
