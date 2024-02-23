package com.wu.framework.easy.mysql.binlog.listener.serialization;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.serialization.RecordSerialization;


public class MysqlRecordSerialization<Schema, Payload> implements RecordSerialization<Schema, Payload> {

    @Override
    public ConsumerRecord<Schema, Payload> serialization(Object source) {
        return null;
    }

    @Override
    public boolean handle(Class targetClass) {
        return false;
    }
}
