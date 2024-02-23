package com.wu.framework.easy.listener.core.consumer;

import lombok.Data;

/**
 * description 消息记录中的范型数据
 *
 * @author Jia wei Wu
 * @date 11:07 上午
 */
@Data
public class ConsumerRecordType {


    /**
     * 数据结构 schema
     */
    Class schemaType;

    /**
     * 数据负载 payload
     */
    Class payloadType;

}