package com.wu.framework.easy.rocketmq.listener.serialization;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.serialization.RecordSerialization;


public class RocketRecordSerialization<Schema, Payload> implements RecordSerialization<Schema, Payload> {

    @Override
    public ConsumerRecord<Schema, Payload> serialization(Object source) {
        return null;
    }

    /**
     * 判断是是否是需要的 序列化执行器 处理
     *
     * @param targetClass
     */
    @Override
    public boolean handle(Class targetClass) {
        return false;
    }
}
