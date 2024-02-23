package com.wu.smart.acw.core.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe : 应用
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/30 00:15
 */
@Data
@Accessors(chain = true)
public class ApplicationDto {

    /**
     * 主键
     */
    @Schema( description = "主键")
    private Long applicationId;
    /**
     * 项目ID
     */
    @Schema( description = "项目ID")
    private Long projectId;

    /**
     * 项目
     */
    @Schema(required = false, description = "项目")
    private String projectName;
    /**
     * 应用名称
     */
    @Schema( description = "应用名称")
    private String applicationName;

    /**
     * 数据库名称
     */
    @Schema(description = "数据库名称")
    private String schemaName;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

}
