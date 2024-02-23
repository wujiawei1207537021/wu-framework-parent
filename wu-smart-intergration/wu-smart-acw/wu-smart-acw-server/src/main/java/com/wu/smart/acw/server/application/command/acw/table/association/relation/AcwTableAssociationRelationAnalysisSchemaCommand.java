package com.wu.smart.acw.server.application.command.acw.table.association.relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * 表与表之间的关系 参数
 */
@Data
public class AcwTableAssociationRelationAnalysisSchemaCommand {
    /**
     * 数据库实例schema
     */
    @Schema(description = "数据库实例schema", name = "schema")
    private String schema;


    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 解析阀值 当统计的关系大于者数据时记录
     */
    @Schema(description = "解析阀值 当统计的关系大于者数据时记录", name = "threshold")
    private double relationThreshold = 50;

    /**
     * 忽略的字段
     */
    @Schema(description = "忽略的字段")
    private List<String> ignoreFieldList;
}
