package com.wu.framework.easy.listener.core.consumer;

/**
 * 消费者记录
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/17 6:33 下午
 */
public abstract class AbstractDefaultConsumerRecord<Schema, Payload> extends AbstractConsumerRecord<Schema, Payload> {

    public AbstractDefaultConsumerRecord(Schema schema, Payload payload) {
        super(schema, payload);
    }
}