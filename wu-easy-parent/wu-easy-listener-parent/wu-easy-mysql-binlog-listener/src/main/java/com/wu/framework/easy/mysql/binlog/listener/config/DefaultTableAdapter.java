package com.wu.framework.easy.mysql.binlog.listener.config;


import com.wu.framework.easy.mysql.binlog.listener.serialization.TableInfo;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/5/13 3:34 下午
 */
@ConditionalOnProperty(prefix = "spring.datasource", value = "enable-binlog", havingValue = "true")
@ConditionalOnMissingBean(TableAdapter.class)
public class DefaultTableAdapter implements TableAdapter {


    private final LazyLambdaStream lazyLambdaStream;
    Map<String, TableInfo> map = new ConcurrentHashMap<>();
    Map<Long, TableInfo> TABLE_ID_MAP = new ConcurrentHashMap<>();

    public DefaultTableAdapter(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }


    /**
     * description 根据tableID获取表信息
     *
     * @param tableId 表ID
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 3:57 下午
     */
    @Override
    public TableInfo getTable(long tableId) {
        if (TABLE_ID_MAP.containsKey(tableId)) {
            return TABLE_ID_MAP.get(tableId);
        }
        throw new RuntimeException("根据tableID获取表信息 无法找到表信息，表ID为：" + tableId);
    }

    /**
     * description 本地是否存在表ID信息
     *
     * @param tableId 表id
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 4:03 下午
     */
    @Override
    public Boolean existsTableId(long tableId) {
        return TABLE_ID_MAP.containsKey(tableId);
    }

    /**
     * description 缓存表
     *
     * @param tableId   表ID
     * @param schema    数据库
     * @param tableName 表名
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 4:21 下午
     */
    @Override
    public void cacheTable(long tableId, String schema, String tableName) {
        if (!existsTableId(tableId)) {
            Collection<LazyColumn> collection = lazyLambdaStream.of(LazyColumn.class).select(LazyWrappers.<LazyColumn>lambdaWrapper()
                    .eq(LazyColumn::getTableSchema, schema)
                    .eq(LazyColumn::getTableName, tableName)).collection();
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            tableInfo.setSchema(schema);
            tableInfo.setLazyColumnList(collection);
            TABLE_ID_MAP.put(tableId, tableInfo);
        }
    }

}
