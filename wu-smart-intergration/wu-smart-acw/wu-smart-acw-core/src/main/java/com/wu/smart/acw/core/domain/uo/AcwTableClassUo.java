package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表和class的关联表
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "表和class的关联表")
public class AcwTableClassUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    @LazyTableFieldUnique
    private Long classId;
    /**
     * project id
     */
    @LazyTableFieldUnique
    private Long projectId;
    /**
     * 数据库服务器ID
     */
    @Schema(description = "数据库服务器ID")
    private String instanceId;
    /**
     * 数据库名称
     */
    @LazyTableField(name = "schema")
    private String schema;
    /**
     * table name
     */
    @LazyTableFieldUnique
    private String tableName;


    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除")
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

}
