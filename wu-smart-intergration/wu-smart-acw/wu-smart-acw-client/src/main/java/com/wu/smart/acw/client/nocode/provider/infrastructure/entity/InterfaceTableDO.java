package com.wu.smart.acw.client.nocode.provider.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 接口与表的关联
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface_table", comment = "接口与表的关联")
@Schema(title = "interface_table", description = "接口与表的关联")
public class InterfaceTableDO {


    /**
     * 主键 ID
     */
    @LazyTableFieldId(upsertStrategy = LazyFieldStrategy.NEVER)
    private Integer id;
    /**
     * api_id
     */
    @Schema(description = "apiId", name = "apiId")
    @LazyTableFieldUnique(name = "api_id", comment = "api_id")
    private Integer apiId;

    /**
     * 表名称
     */
    @Schema(description = "表名称", name = "tableName")
    @LazyTableFieldUnique(name = "table_name", comment = "表名称")
    private String tableName;

    /**
     * 是否主表
     */
    @Schema(description = "是否主表", name = "isMainTable")
    @LazyTableField(name = "is_main_table", comment = "是否主表")
    private Boolean isMainTable;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'", columnType = "tinyint(1)")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}