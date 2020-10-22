package com.supconit.its.transform.kafka;

import com.supconit.its.transform.config.DataProcessConfig;
import com.supconit.its.transform.entity.KafkaJsonMessageWrapper;
import com.supconit.its.transform.entity.kafka.KafkaJsonMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * kafka生产者
 *
 * @author 黄珺
 * @date 2019/8/30
 **/
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaJsonMessage> kafkaTemplate;

    private final DataProcessConfig dataProcessConfig;

    @Autowired
    public KafkaProducer(@Qualifier("transformKafkaTemplate") KafkaTemplate<String, KafkaJsonMessage> kafkaTemplate, DataProcessConfig dataProcessConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.dataProcessConfig = dataProcessConfig;
    }

    public ListenableFuture<SendResult<String, KafkaJsonMessage>> send(KafkaJsonMessageWrapper kafkaMessage) {
        String topic = kafkaMessage.getTopic();
        if (topic == null) {
            topic = dataProcessConfig.getDefaultKafkaTargetTopic();
        }
        ProducerRecord<String, KafkaJsonMessage> record =
                new ProducerRecord<>(topic, null, kafkaMessage.getTimestamp(), kafkaMessage.getKey(), kafkaMessage.getValue());
        return kafkaTemplate.send(record);
    }
}
