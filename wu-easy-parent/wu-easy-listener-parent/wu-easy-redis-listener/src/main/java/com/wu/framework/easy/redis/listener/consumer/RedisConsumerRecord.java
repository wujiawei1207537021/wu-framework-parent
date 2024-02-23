package com.wu.framework.easy.redis.listener.consumer;

import com.wu.framework.easy.listener.core.consumer.AbstractConsumerRecord;


public class RedisConsumerRecord<Schema, Payload> extends AbstractConsumerRecord<Schema, Payload> {


    public RedisConsumerRecord(Schema schema, Payload payload) {
        super(schema, payload);
    }
}
