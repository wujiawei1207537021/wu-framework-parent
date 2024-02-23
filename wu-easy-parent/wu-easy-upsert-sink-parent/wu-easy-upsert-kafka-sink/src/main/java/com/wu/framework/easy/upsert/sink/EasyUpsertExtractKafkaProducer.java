package com.wu.framework.easy.upsert.sink;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringBootKafkaConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * description EasyUpsertExtractKafkaProducer
 *
 * @author Jia wei Wu
 * @date 2020/7/16 下午1:27
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.kafka", value = "bootstrap-servers")
public class EasyUpsertExtractKafkaProducer implements InitializingBean {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    static {
        //取消时间的转化格式，默认是时间戳,同时需要设置要表现的时间格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        JavaTimeModule javaTimeModule = new JavaTimeModule();   // 默认序列化没有实现，反序列化有实现
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
        OBJECT_MAPPER.registerModule(javaTimeModule);
        // 设置时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getDefault());
        // 设置格式化输出
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 设置蛇形格式
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }


    private final KafkaProducer<String, String> kafkaProducer;


    public EasyUpsertExtractKafkaProducer(SpringBootKafkaConfigProperties springBootKafkaConfigtProperties) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, springBootKafkaConfigtProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.kafkaProducer = new KafkaProducer<>(props);
    }


    /**
     * 异步发送数据
     *
     * @param code  业务码
     * @param topic 目标topic
     * @param value 数据
     */
    public Future<RecordMetadata> sendAsync(String code, String topic, Object value) {
        return sendAsync(code, topic, value, null);
    }


    /**
     * 异步发送数据，并在成功后执行回调
     *
     * @param code     业务码
     * @param topic    目标topic
     * @param value    数据
     * @param callback 发送成功后回调
     */
    public Future<RecordMetadata> sendAsync(String code, String topic, Object value, Callback callback) {
        String message = (value instanceof String) ? (String) value : toJsonString(value);
        ProducerRecord<String, String> record;
//        dataAccessLogHelper.trySendExtractLog(code, topic, value);//TODO
        record = new ProducerRecord<>(topic, message);
        return kafkaProducer.send(record, callback);
    }

    /**
     * 同步发送数据，默认等待2秒
     *
     * @param code  业务码
     * @param topic 目标topic
     * @param value 数据
     */
    public void sendSync(String code, String topic, Object value) throws InterruptedException, ExecutionException, TimeoutException {
        sendSync(code, topic, value, 2);
    }

    /**
     * 同步发送数据，等待指定秒数
     *
     * @param code       业务码
     * @param topic      目标topic
     * @param value      数据
     * @param waitSecond 等待秒数
     */
    public void sendSync(String code, String topic, Object value, int waitSecond) throws InterruptedException, ExecutionException, TimeoutException {
        sendAsync(code, topic, value, null).get(waitSecond, TimeUnit.SECONDS);
    }

    private String toJsonString(Object object) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换为Json字符串出错:" + e);
        }
        return json;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(" init EasyUpsertExtractKafkaProducer success");
    }
}
