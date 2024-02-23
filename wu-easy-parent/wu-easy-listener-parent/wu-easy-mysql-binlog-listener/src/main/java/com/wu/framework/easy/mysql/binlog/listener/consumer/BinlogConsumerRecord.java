package com.wu.framework.easy.mysql.binlog.listener.consumer;


import com.wu.framework.easy.listener.core.consumer.AbstractConsumerRecord;

/**
 * @author wujiawei
 */
public class BinlogConsumerRecord<Schema, Payload> extends AbstractConsumerRecord<Schema, Payload> {


    public BinlogConsumerRecord(Schema schema, Payload payload) {
        super(schema, payload);
    }
}
