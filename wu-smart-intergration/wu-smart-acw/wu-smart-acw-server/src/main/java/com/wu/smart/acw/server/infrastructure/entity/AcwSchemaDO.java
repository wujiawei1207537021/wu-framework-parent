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
 * describe ACW 数据库库信息
 *
 * @author Jia wei Wu
 * @date 2023/10/24 03:15 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_schema",  comment = "ACW 数据库库信息")
@Schema(title = "acw_schema", description = "ACW 数据库库信息")
public class AcwSchemaDO {


    /**
     * null
     */
    @Schema(description = "null", name = "characterSet")
    @LazyTableField(name = "character_set", comment = "null", columnType = "varchar(255)")
    private String characterSet;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称", name = "databaseSchemaName")
    @LazyTableField(name = "database_schema_name", comment = "数据库名称", lazyTableIndexs = {@LazyTableIndex(indexName = "d_i", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String databaseSchemaName;

    /**
     * null
     */
    @Schema(description = "null", name = "ext")
    @LazyTableField(name = "ext", comment = "null", columnType = "varchar(255)")
    private String ext;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

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
    @LazyTableField(name = "instance_id", comment = "数据库实例ID", notNull = true, lazyTableIndexs = {@LazyTableIndex(indexName = "d_i", indexType = LayerField.LayerFieldType.UNIQUE), @LazyTableIndex(indexName = "i_s", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String instanceId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称", name = "schemaName")
    @LazyTableField(name = "schema_name", comment = "数据库名称", lazyTableIndexs = {@LazyTableIndex(indexName = "i_s", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String schemaName;

    /**
     * null
     */
    @Schema(description = "null", name = "sortingRules")
    @LazyTableField(name = "sorting_rules", comment = "null", columnType = "varchar(255)")
    private String sortingRules;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}