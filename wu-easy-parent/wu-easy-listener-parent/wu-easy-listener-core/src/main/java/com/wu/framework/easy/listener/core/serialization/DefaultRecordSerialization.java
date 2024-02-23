package com.wu.framework.easy.listener.core.serialization;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;

/**
 * describe : DefaultRecordSerialization
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/13 9:29 下午
 */
public class DefaultRecordSerialization<Schema, Payload> implements RecordSerialization<Schema, Payload> {

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
    public boolean handle(Class<? extends ConsumerRecord> targetClass) {
        return false;
    }

}
