package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 数据库表字段
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/6 23:37
 */
@Data
@LazyTable(comment = "数据库表字段")
@Deprecated
public class AcwTableColumnUo {

    @LazyTableFieldId
    private Long id;
    /**
     * 表ID
     */
    @LazyTableField(comment = "表ID")
    private Long tableId;
    /**
     * 字符八位字节长度
     */
    private String characterOctetLength;
    private String collationName;
    /**
     * 表名
     */
    @LazyTableFieldUnique(comment = "表名", columnType = "varchar(100)")
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
    @LazyTableField(columnType = "text")
    private String columnType;
    private String numericPrecision;
    private String privileges;
    @LazyTableField(columnType = "text")
    private String columnComment;
    private String characterSetName;
    @LazyTableField(columnType = "text")
    private String generationExpression;
    /**
     * varchar、int、text、bigint
     */
    private String dataType;
    /**
     * 字段名
     */
    @LazyTableFieldUnique(comment = "字段名", columnType = "varchar(100)")
    private String columnName;
    private String srsId;
    private String numericScale;
    private Long datetimePrecision;
    private Long characterMaximumLength;

    @LazyTableFieldUnique(comment = "catalog", columnType = "varchar(20)")
    private String tableCatalog;
    @LazyTableFieldUnique(comment = "数据库", columnType = "varchar(50)")
    private String schemaName;
    private String columnDefault;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'")
    private Boolean isDeleted = false;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;
}
