package com.wu.framework.easy.mysql.listener.consumer;

import com.wu.framework.easy.listener.core.consumer.AbstractConsumerRecord;


public class MySQLConsumerRecord<Schema, Payload> extends AbstractConsumerRecord<Schema, Payload> {


    public MySQLConsumerRecord(Schema schema, Payload payload) {
        super(schema, payload);
    }
}
