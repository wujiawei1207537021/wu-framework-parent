package com.wu.smart.acw.server.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据库表填充记录
 */
@Data
public class AcwTableAutoStuffedRecordCommand {

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 数据库实例名称
     */
    @Schema(description = "数据库实例名称", name = "instanceName")
    private String instanceName;


    /**
     * 数据库schema ID
     */
    @Schema(description = "数据库schema ID", name = "databaseSchemaId")
    private Long databaseSchemaId;
    /**
     * 数据库schemaName
     */
    @Schema(description = "数据库schema名称", name = "schemaName")
    private String schemaName;

    /**
     * 自动填充表
     */
    @Schema(description = "自动填充表", name = "tableNameList")
    private List<String> tableNameList;

    /**
     * 自动填充数量
     */
    @Schema(description = "自动填充数量", name = "autoStuffedNum")
    private Long autoStuffedNum;
    /**
     * 自动填充状态
     */
    @Schema(description = "自动填充状态", name = "status")
    private Boolean status;
    /**
     * 自动填充数据记录ID
     */
    @Schema(description = "自动填充数据记录ID", name = "id")
    private String id;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

}
