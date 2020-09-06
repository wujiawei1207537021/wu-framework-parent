package com.supconit.its.transform.entity.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

import java.util.Map;

import static com.supconit.its.transform.util.JsonUtils.convertObject;
import static com.supconit.its.transform.util.JsonUtils.parseObject;


/**
 * kafka connect json格式数据，需要带有json schema
 *
 * @param <T> value的类型
 * @author 黄珺
 * @date 2019-08-30
 */
@Data
public class KafkaJsonMessage<T> {

    public static TargetJsonSchema defaultSchema;
    public static Map<String, TargetJsonSchema> targetSchemaMap;

    /**
     * 数据类型的json schema
     */
    private TargetJsonSchema schema;

    /**
     * json格式载荷数据
     */
    private T payload;

    public KafkaJsonMessage() {
    }

    public KafkaJsonMessage(TargetJsonSchema schema) {
        this.schema = schema;
    }

    public KafkaJsonMessage(TargetJsonSchema schema, T payload) {
        this.schema = schema;
        this.payload = payload;
    }

    public static KafkaJsonMessage newInstance() {
        return new KafkaJsonMessage(defaultSchema);
    }

    public static <T> KafkaJsonMessage<T> newInstance(T payload) {
        return new KafkaJsonMessage<>(defaultSchema, payload);
    }

    public static <T> KafkaJsonMessage<T> newInstance(T payload, String schemaName) {
        if (schemaName == null) {
            return new KafkaJsonMessage<>(defaultSchema, payload);
        }

        TargetJsonSchema targetJsonSchema = targetSchemaMap.get(schemaName);
        if (targetJsonSchema == null) {
            throw new IllegalArgumentException("错误的schema名称");
        }
        return new KafkaJsonMessage<>(targetJsonSchema, payload);
    }

    /**
     * 获取kafka消息中的载荷数据
     *
     * @param formValue   kafka message
     * @param payloadType 载荷类型
     */
    public static <T> T getKafkaPayload(String formValue, Class<T> payloadType) {
        KafkaJsonMessage<Map> message = parseObject(formValue, new TypeReference<KafkaJsonMessage<Map>>() {
        });
        return convertObject(message.getPayload(), payloadType);
    }
}
