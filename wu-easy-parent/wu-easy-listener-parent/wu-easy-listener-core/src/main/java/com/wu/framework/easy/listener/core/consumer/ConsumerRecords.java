package com.wu.framework.easy.listener.core.consumer;

/**
 * 消费者记录
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/10/17 6:34 下午
 */
public interface ConsumerRecords<Schema, Payload> extends Iterable<ConsumerRecord<Schema, Payload>> {


}