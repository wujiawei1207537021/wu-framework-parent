package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@LazyTable(schema = "information_schema", tableName = "tables")
public final class LazyTableInfo {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private String version;
    private String rowFormat;
    private Long tableRows;
    private String avgRowLength;
    private String dataLength;
    private String maxDataLength;
    private String indexLength;
    private String dataFree;
    private String autoIncrement;
    private String createTime;
    private String updateTime;
    private String checkTime;
    private String tableCollation;
    private String checksum;
    private String createOptions;
    private String tableComment;
}