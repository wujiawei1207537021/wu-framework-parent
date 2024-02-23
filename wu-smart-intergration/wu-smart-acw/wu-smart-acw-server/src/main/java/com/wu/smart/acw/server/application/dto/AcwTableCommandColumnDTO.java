package com.wu.smart.acw.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 数据库表字段（即将弃用）
 *
 * @author Jia wei Wu
 * @date 2023/11/04 06:27 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_column_command_dto", description = "数据库表字段（即将弃用）")
public class AcwTableCommandColumnDTO {

    private List<AcwTableColumnDTO> acwTableColumnDTOList;

    private List<AcwTableColumnIndexDTO> acwTableColumnIndexDTOList;


    /**
     * 字段信息
     */
    @Data
    public static class AcwTableColumnDTO {

        /**
         * null
         */
        @Schema(description = "null", name = "characterMaximumLength")
        private Long characterMaximumLength;

        /**
         * null
         */
        @Schema(description = "null", name = "characterOctetLength")
        private String characterOctetLength;

        /**
         * null
         */
        @Schema(description = "null", name = "characterSetName")
        private String characterSetName;

        /**
         * null
         */
        @Schema(description = "null", name = "collationName")
        private String collationName;

        /**
         *
         */
        @Schema(description = "", name = "columnComment")
        private String columnComment;

        /**
         * null
         */
        @Schema(description = "null", name = "columnDefault")
        private String columnDefault;

        /**
         * null
         */
        @Schema(description = "null", name = "columnKey")
        private String columnKey;

        /**
         * 字段名
         */
        @Schema(description = "字段名", name = "columnName")
        private String columnName;

        /**
         *
         */
        @Schema(description = "", name = "columnType")
        private String columnType;

        /**
         * 创建时间
         */
        @Schema(description = "创建时间", name = "createTime")
        private LocalDateTime createTime;

        /**
         * null
         */
        @Schema(description = "null", name = "dataType")
        private String dataType;

        /**
         * null
         */
        @Schema(description = "null", name = "datetimePrecision")
        private Long datetimePrecision;

        /**
         * null
         */
        @Schema(description = "null", name = "extra")
        private String extra;

        /**
         *
         */
        @Schema(description = "", name = "generationExpression")
        private String generationExpression;

        /**
         *
         */
        @Schema(description = "", name = "id")
        private Long id;

        /**
         * 是否删除
         */
        @Schema(description = "是否删除", name = "isDeleted")
        private Boolean isDeleted;

        /**
         * null
         */
        @Schema(description = "null", name = "isNullable")
        private String isNullable;

        /**
         * null
         */
        @Schema(description = "null", name = "numericPrecision")
        private String numericPrecision;

        /**
         * null
         */
        @Schema(description = "null", name = "numericScale")
        private String numericScale;

        /**
         * null
         */
        @Schema(description = "null", name = "ordinalPosition")
        private Long ordinalPosition;

        /**
         * null
         */
        @Schema(description = "null", name = "privileges")
        private String privileges;

        /**
         * 数据库
         */
        @Schema(description = "数据库", name = "schemaName")
        private String schemaName;

        /**
         * null
         */
        @Schema(description = "null", name = "srsId")
        private String srsId;

        /**
         * catalog
         */
        @Schema(description = "catalog", name = "tableCatalog")
        private String tableCatalog;

        /**
         * 表ID
         */
        @Schema(description = "表ID", name = "tableId")
        private Long tableId;

        /**
         * 表名
         */
        @Schema(description = "表名", name = "tableName")
        private String tableName;

        /**
         * 更新时间
         */
        @Schema(description = "更新时间", name = "updateTime")
        private LocalDateTime updateTime;
    }

    /**
     * 字段索引
     */
    @Data
    public static
    class AcwTableColumnIndexDTO {

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
        private List<String> columnNameList;
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
}