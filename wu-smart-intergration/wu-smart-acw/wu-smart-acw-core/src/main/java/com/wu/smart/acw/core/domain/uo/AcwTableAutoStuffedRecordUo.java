package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.lazy.stereotype.field.LazyFieldSortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库表填充记录
 */
@Data
@LazyTable( comment = "数据库表填充记录")
public class AcwTableAutoStuffedRecordUo {

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableField(name = "instance_id", comment = "数据库实例ID")
    private String instanceId;

    /**
     * 数据库实例名称
     */
    @Schema(description = "数据库实例名称", name = "instanceName")
    @LazyTableField(comment = "数据库实例名称")
    private String instanceName;


    /**
     * 数据库schema ID
     */
    @Schema(description = "数据库schema ID", name = "databaseSchemaId")
    @LazyTableField(name = "database_schema_id", comment = "数据库schema ID")
    private Long databaseSchemaId;
    /**
     * 数据库schemaName
     */
    @Schema(description = "数据库schema名称", name = "schemaName")
    @LazyTableField(comment = "数据库schema名称")
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
     * 自动填充状态
     */
    @Schema(description = "自动填充状态", name = "status")
    @LazyTableField(comment = "自动填充状态")
    private Boolean status;
    /**
     * 自动填充数据记录ID
     */
    @Schema(description = "自动填充数据记录ID", name = "id")
    @LazyTableField(name = "id", comment = "自动填充数据记录ID", lazyTableIndexs = {@LazyTableIndex(indexType = LayerField.LayerFieldType.UNIQUE)})
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
    @LazyFieldSortOrder(sortOrder = LazyFieldSortOrder.SortOrder.DESC)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}
