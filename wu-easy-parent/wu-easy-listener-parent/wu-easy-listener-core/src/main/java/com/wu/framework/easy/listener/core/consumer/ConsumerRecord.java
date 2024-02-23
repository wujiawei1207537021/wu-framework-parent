package com.wu.framework.easy.listener.core.consumer;

/**
 * 消费者记录
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/17 6:33 下午
 */
public interface ConsumerRecord<Schema, Payload> {

    // 数据结构 schema
    Schema schema();

    // 数据负载 payload
    Payload payload();

}