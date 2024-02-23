package com.wu.framework.easy.mysql.listener.config;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/11/13 10:16 下午
 */
@Accessors(chain = true)
@Data
@LazyTable(schema = "mysql", tableName = "listener_consumer_log")
public class ListenerConsumerLog {

    @LazyTableFieldUnique
    private String tableName;

    // 消费者
    @LazyTableFieldUnique
    private String consumer;
    // 数据库
    @LazyTableFieldUnique
    private String databaseName;

    // 偏移量
    @LazyTableField
    private long offSet;

}
