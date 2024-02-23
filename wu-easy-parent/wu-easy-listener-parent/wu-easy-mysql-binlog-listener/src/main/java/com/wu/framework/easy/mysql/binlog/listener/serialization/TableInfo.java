package com.wu.framework.easy.mysql.binlog.listener.serialization;

import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description 数据库表信息
 *
 * @author Jia wei Wu
 * @date 2022/5/13 3:36 下午
 */
@Data
public class TableInfo {

    /**
     * binlog  表ID
     */
    private long tableId;

    /**
     * 库名称
     */
    private String schema;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 列表
     */
    private Collection<LazyColumn> lazyColumnList;

    /**
     * 行 ordinalPosition 对应的字段
     */
    private Map<Long, LazyColumn> ordinalPositionMap = new ConcurrentHashMap();

    public Map<Long, LazyColumn> getOrdinalPositionMap() {
        if (ObjectUtils.isEmpty(ordinalPositionMap)) {
            this.ordinalPositionMap = lazyColumnList.stream().collect(Collectors.toMap(LazyColumn::getOrdinalPosition, Function.identity()));
        }
        return ordinalPositionMap;
    }
}
