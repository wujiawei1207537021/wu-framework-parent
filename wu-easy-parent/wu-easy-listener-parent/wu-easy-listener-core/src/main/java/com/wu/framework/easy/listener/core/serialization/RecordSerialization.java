package com.wu.framework.easy.listener.core.serialization;

import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;

/**
 * describe : RecordSerialization
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/13 9:29 下午
 */
public interface RecordSerialization<Schema, Payload> {


    /**
     * 序列化原始数据
     *
     * @param source
     * @return
     */
    ConsumerRecord<Schema, Payload> serialization(Object source);

    /**
     * 判断是是否是需要的 序列化执行器 处理
     */
    boolean handle(Class<? extends ConsumerRecord> targetClass);

}
