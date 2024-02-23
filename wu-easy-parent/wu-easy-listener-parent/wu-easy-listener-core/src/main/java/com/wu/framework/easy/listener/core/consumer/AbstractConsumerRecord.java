package com.wu.framework.easy.listener.core.consumer;

/**
 * 消费者记录
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/17 6:33 下午
 */
public abstract class AbstractConsumerRecord<Schema, Payload> implements ConsumerRecord<Schema, Payload> {

    /**
     * 数据结构 schema
     */
    private Schema schema;
    /**
     * 数据负载 payload
     */
    private Payload payload;


    public AbstractConsumerRecord(Schema schema, Payload payload) {
        this.schema = schema;
        this.payload = payload;
    }

    @Override
    public Schema schema() {
        return schema;
    }

    @Override
    public Payload payload() {
        return payload;
    }
}