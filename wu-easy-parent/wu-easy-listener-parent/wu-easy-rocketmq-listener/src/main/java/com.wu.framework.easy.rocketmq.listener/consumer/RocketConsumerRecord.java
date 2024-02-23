package com.wu.framework.easy.rocketmq.listener.consumer;

import com.wu.framework.easy.listener.core.consumer.AbstractConsumerRecord;


public class RocketConsumerRecord<Schema, Payload> extends AbstractConsumerRecord<Schema, Payload> {


    public RocketConsumerRecord(Schema schema, Payload payload) {
        super(schema, payload);
    }
}
