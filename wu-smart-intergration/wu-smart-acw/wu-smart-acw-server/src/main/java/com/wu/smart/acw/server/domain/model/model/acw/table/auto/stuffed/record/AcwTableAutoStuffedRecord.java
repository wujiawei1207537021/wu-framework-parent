package com.wu.smart.acw.server.domain.model.model.acw.table.auto.stuffed.record;

import com.wu.smart.acw.server.translation.InstanceIdTranslation2NameField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 数据库表填充记录
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_auto_stuffed_record", description = "数据库表填充记录")
public class AcwTableAutoStuffedRecord {


    /**
     * 自动填充数量
     */
    @Schema(description = "自动填充数量", name = "autoStuffedNum")
    private Long autoStuffedNum;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 数据库schema ID
     */
    @Schema(description = "数据库schema ID", name = "databaseSchemaId")
    private Long databaseSchemaId;

    /**
     * 自动填充数据记录ID
     */
    @Schema(description = "自动填充数据记录ID", name = "id")
    private String id;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 数据库实例名称
     */
    @InstanceIdTranslation2NameField(translationSourceName = "instanceId")
    @Schema(description = "数据库实例名称", name = "instanceName")
    private String instanceName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 数据库schema名称
     */
    @Schema(description = "数据库schema名称", name = "schemaName")
    private String schemaName;

    /**
     * 自动填充状态
     */
    @Schema(description = "自动填充状态", name = "status")
    private Boolean status;

    /**
     * 自动填充表
     */
    @Schema(description = "自动填充表", name = "tableName")
    private String tableName;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

}