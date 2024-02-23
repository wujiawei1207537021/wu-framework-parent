package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;

@Data
@LazyTable(schema = "information_schema", tableName = "COLUMNS")
public final class LazyColumn {
    /**
     * 字符八位字节长度
     */
    private String characterOctetLength;
    private String collationName;
    private String tableName;
    private String columnKey;
    /**
     * 额外的
     * auto_increment(自增)
     */
    private String extra;
    private String isNullable;
    private Long ordinalPosition;
    /**
     * varchar(20)
     */
    private String columnType;
    private String numericPrecision;
    private String privileges;
    private String columnComment;
    private String characterSetName;
    private String generationExpression;
    /**
     * varchar、int、text、bigint
     */
    private String dataType;
    private String columnName;
    private String srsId;
    private String numericScale;
    private Long datetimePrecision;
    private Long characterMaximumLength;
    private String tableCatalog;
    private String tableSchema;
    private String columnDefault;
}
