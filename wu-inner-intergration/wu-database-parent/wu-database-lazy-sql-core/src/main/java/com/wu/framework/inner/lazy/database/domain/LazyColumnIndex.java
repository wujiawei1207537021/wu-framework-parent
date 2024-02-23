package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@LazyTable(tableName = "STATISTICS", schema = "information_schema", comment = "null")
public class LazyColumnIndex {

    /**
     * tableCatalog
     */
    private String tableCatalog;
    /**
     * 数据库schema
     */
    private String tableSchema;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 不是唯一的
     */
    private boolean nonUnique;
    /**
     * indexSchema
     */
    private String indexSchema;
    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 序列索引
     */
    private String seqInIndex;
    /**
     * 行字段名称
     */
    private String columnName;
    /**
     * 整理
     */
    private String collation;
    /**
     * 基数
     */
    private String cardinality;
    /**
     * 子部分
     */
    private String subPart;
    /**
     * 包装好的
     */
    private String packed;
    /**
     * yes 允许为空，no 不允许为空
     */
    private String nullable;
    /**
     * 索引类型 如 BTREE、HASH
     */
    private String indexType;
    /**
     * 描述注解
     */
    private String comment;
    /**
     * 索引描述
     */
    private String indexComment;
    /**
     * yes 可见的、no 不可见的
     */
    private Boolean isVisible;
}
