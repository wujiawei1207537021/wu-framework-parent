package com.wu.framework.easy.upsert.autoconfigure.enums;

/**
 * description 类型
 *
 * @author Jia wei Wu
 * @date 2020/9/15 下午2:54
 */
public enum EasyUpsertType {
    // 空的
    EMPTY,
    // 自动选取
    AUTO,
    //
    MySQL,
    KAFKA,
    ES,
    REDIS,
    HBASE,
    INFLUXDB,
    H2,
    PULSAR
    ;
}
