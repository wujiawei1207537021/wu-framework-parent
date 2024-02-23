package com.wu.smart.acw.server.domain.model.model.acw.table.column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 数据库表字段（即将弃用） 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_column",description = "数据库表字段（即将弃用）")
public class AcwTableColumn {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="characterMaximumLength",example = "")
    private Long characterMaximumLength;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="characterOctetLength",example = "")
    private String characterOctetLength;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="characterSetName",example = "")
    private String characterSetName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="collationName",example = "")
    private String collationName;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="columnComment",example = "")
    private String columnComment;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="columnDefault",example = "")
    private String columnDefault;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="columnKey",example = "")
    private String columnKey;

    /**
     * 
     * 字段名
     */
    @Schema(description ="字段名",name ="columnName",example = "")
    private String columnName;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="columnType",example = "")
    private String columnType;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="dataType",example = "")
    private String dataType;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="datetimePrecision",example = "")
    private Long datetimePrecision;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="extra",example = "")
    private String extra;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="generationExpression",example = "")
    private String generationExpression;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="isNullable",example = "")
    private String isNullable;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="numericPrecision",example = "")
    private String numericPrecision;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="numericScale",example = "")
    private String numericScale;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="ordinalPosition",example = "")
    private Long ordinalPosition;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="privileges",example = "")
    private String privileges;

    /**
     * 
     * 数据库
     */
    @Schema(description ="数据库",name ="schemaName",example = "")
    private String schemaName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="srsId",example = "")
    private String srsId;

    /**
     * 
     * catalog
     */
    @Schema(description ="catalog",name ="tableCatalog",example = "")
    private String tableCatalog;

    /**
     * 
     * 表ID
     */
    @Schema(description ="表ID",name ="tableId",example = "")
    private Long tableId;

    /**
     * 
     * 表名
     */
    @Schema(description ="表名",name ="tableName",example = "")
    private String tableName;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}