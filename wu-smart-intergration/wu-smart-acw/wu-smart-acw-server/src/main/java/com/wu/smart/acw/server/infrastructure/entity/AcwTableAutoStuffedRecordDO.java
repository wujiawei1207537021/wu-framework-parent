package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库表填充记录
 */
@Data
@LazyTable(comment = "数据库表填充记录")
public class AcwTableAutoStuffedRecordDO {

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableField(name = "instance_id", comment = "数据库实例ID")
    private String instanceId;

    /**
     * 数据库schema ID
     */
    @Schema(description = "数据库schema ID", name = "databaseSchemaId")
    @LazyTableField(name = "schema_id", comment = "数据库schema ID")
    private Long databaseSchemaId;

    /**
     * 数据库schema名称
     */
    @Schema(description = "数据库schema名称", name = "schemaName")
    @LazyTableField(name = "schema_name", comment = "数据库schema名称")
    private String schemaName;


    /**
     * 自动填充表
     */
    @Schema(description = "自动填充表", name = "tableName")
    @LazyTableField(comment = "自动填充表")
    private String tableName;

    /**
     * 自动填充数量
     */
    @Schema(description = "自动填充数量", name = "autoStuffedNum")
    @LazyTableField(comment = "自动填充数量")
    private Long autoStuffedNum;
    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableField(name = "id", comment = "主键", key = true, lazyTableIndexs = {@LazyTableIndex(indexType = LayerField.LayerFieldType.UNIQUE)})
    private String id;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'")
    private Boolean isDeleted;
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
    /**
     * 初始化数据库到本地
     */
    @LazyTableField(comment = "初始化数据库到本地")
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal;
}
