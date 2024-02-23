package com.wu.framework.inner.layer.data.schema;

/**
 * describe : 数据结构  schema +Payload 负载
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/14 12:04 下午
 */
public interface Structure<Schema, Payload> {

    // 数据结构 schema
    Schema schema();

    // 数据负载 payload
    Payload payload();
}
