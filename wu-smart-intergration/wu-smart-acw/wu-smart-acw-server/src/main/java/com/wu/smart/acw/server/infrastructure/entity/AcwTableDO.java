package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 数据库表信息（即将弃用）
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_table",  comment = "数据库表信息（即将弃用）")
@Schema(title = "acw_table", description = "数据库表信息（即将弃用）")
public class AcwTableDO {


    /**
     * null
     */
    @Schema(description = "null", name = "autoIncrement")
    @LazyTableField(name = "auto_increment", comment = "null", columnType = "varchar(255)")
    private String autoIncrement;

    /**
     * null
     */
    @Schema(description = "null", name = "avgRowLength")
    @LazyTableField(name = "avg_row_length", comment = "null", columnType = "varchar(255)")
    private String avgRowLength;

    /**
     * null
     */
    @Schema(description = "null", name = "checkTime")
    @LazyTableField(name = "check_time", comment = "null", columnType = "varchar(255)")
    private String checkTime;

    /**
     * null
     */
    @Schema(description = "null", name = "checksum")
    @LazyTableField(name = "checksum", comment = "null", columnType = "varchar(255)")
    private String checksum;

    /**
     * null
     */
    @Schema(description = "null", name = "createOptions")
    @LazyTableField(name = "create_options", comment = "null", columnType = "varchar(255)")
    private String createOptions;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * null
     */
    @Schema(description = "null", name = "dataFree")
    @LazyTableField(name = "data_free", comment = "null", columnType = "varchar(255)")
    private String dataFree;

    /**
     * null
     */
    @Schema(description = "null", name = "dataLength")
    @LazyTableField(name = "data_length", comment = "null", columnType = "varchar(255)")
    private String dataLength;

    /**
     * null
     */
    @Schema(description = "null", name = "engine")
    @LazyTableField(name = "engine", comment = "null", columnType = "varchar(255)")
    private String engine;

    /**
     *
     */
    @Schema(description = "", name = "id")
    @LazyTableFieldId(name = "id", comment = "")
    private Long id;

    /**
     * null
     */
    @Schema(description = "null", name = "indexLength")
    @LazyTableField(name = "index_length", comment = "null", columnType = "varchar(255)")
    private String indexLength;

    /**
     * 初始化数据库到本地
     */
    @Schema(description = "初始化数据库到本地", name = "initializeToLocal")
    @LazyTableField(name = "initialize_to_local", comment = "初始化数据库到本地", columnType = "tinyint(1)")
    private Boolean initializeToLocal;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableField(name = "instance_id", comment = "数据库实例ID", lazyTableIndexs = {@LazyTableIndex(indexName = "i_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String instanceId;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceName")
    @LazyTableField(name = "instance_name", comment = "数据库实例ID", columnType = "varchar(255)")
    private String instanceName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * null
     */
    @Schema(description = "null", name = "maxDataLength")
    @LazyTableField(name = "max_data_length", comment = "null", columnType = "varchar(255)")
    private String maxDataLength;

    /**
     * null
     */
    @Schema(description = "null", name = "rowFormat")
    @LazyTableField(name = "row_format", comment = "null", columnType = "varchar(255)")
    private String rowFormat;

    /**
     *
     */
    @Schema(description = "", name = "schemaName")
    @LazyTableField(name = "schema_name", comment = "", columnType = "varchar(255)")
    private String schemaName;

    /**
     *
     */
    @Schema(description = "", name = "schemaNameId")
    @LazyTableField(name = "schema_name_id", comment = "", lazyTableIndexs = {@LazyTableIndex(indexName = "i_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "bigint")
    private Long schemaNameId;

    /**
     * null
     */
    @Schema(description = "null", name = "tableCatalog")
    @LazyTableField(name = "table_catalog", comment = "null", columnType = "varchar(255)")
    private String tableCatalog;

    /**
     * null
     */
    @Schema(description = "null", name = "tableCollation")
    @LazyTableField(name = "table_collation", comment = "null", columnType = "varchar(255)")
    private String tableCollation;

    /**
     * null
     */
    @Schema(description = "null", name = "tableComment")
    @LazyTableField(name = "table_comment", comment = "null", columnType = "varchar(255)")
    private String tableComment;

    /**
     *
     */
    @Schema(description = "", name = "tableName")
    @LazyTableField(name = "table_name", comment = "", lazyTableIndexs = {@LazyTableIndex(indexName = "i_s_t", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String tableName;

    /**
     * null
     */
    @Schema(description = "null", name = "tableRows")
    @LazyTableField(name = "table_rows", comment = "null", columnType = "varchar(255)")
    private Long tableRows;

    /**
     * null
     */
    @Schema(description = "null", name = "tableType")
    @LazyTableField(name = "table_type", comment = "null", columnType = "varchar(255)")
    private String tableType;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * null
     */
    @Schema(description = "null", name = "version")
    @LazyTableField(name = "version", comment = "null", columnType = "varchar(255)")
    private String version;

}