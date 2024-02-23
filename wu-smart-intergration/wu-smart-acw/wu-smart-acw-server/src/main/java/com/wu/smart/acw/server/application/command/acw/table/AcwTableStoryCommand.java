package com.wu.smart.acw.server.application.command.acw.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 数据库表信息（即将弃用）
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_story_command", description = "数据库表信息（即将弃用）")
public class AcwTableStoryCommand {


    /**
     * null
     */
    @Schema(description = "null", name = "autoIncrement")
    private String autoIncrement;

    /**
     * null
     */
    @Schema(description = "null", name = "avgRowLength")
    private String avgRowLength;

    /**
     * null
     */
    @Schema(description = "null", name = "checkTime")
    private String checkTime;

    /**
     * null
     */
    @Schema(description = "null", name = "checksum")
    private String checksum;

    /**
     * null
     */
    @Schema(description = "null", name = "createOptions")
    private String createOptions;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * null
     */
    @Schema(description = "null", name = "dataFree")
    private String dataFree;

    /**
     * null
     */
    @Schema(description = "null", name = "dataLength")
    private String dataLength;

    /**
     * null
     */
    @Schema(description = "null", name = "engine")
    private String engine;

    /**
     *
     */
    @Schema(description = "", name = "id")
    private Long id;

    /**
     * null
     */
    @Schema(description = "null", name = "indexLength")
    private String indexLength;

    /**
     * 初始化数据库到本地
     */
    @Schema(description = "初始化数据库到本地", name = "initializeToLocal")
    private Boolean initializeToLocal;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    private String instanceId;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceName")
    private String instanceName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * null
     */
    @Schema(description = "null", name = "maxDataLength")
    private String maxDataLength;

    /**
     * null
     */
    @Schema(description = "null", name = "rowFormat")
    private String rowFormat;

    /**
     *
     */
    @Schema(description = "", name = "schemaName")
    private String schemaName;

    /**
     *
     */
    @Schema(description = "", name = "schemaNameId")
    private Long schemaNameId;

    /**
     * null
     */
    @Schema(description = "null", name = "tableCatalog")
    private String tableCatalog;

    /**
     * null
     */
    @Schema(description = "null", name = "tableCollation")
    private String tableCollation;

    /**
     * null
     */
    @Schema(description = "null", name = "tableComment")
    private String tableComment;

    /**
     *
     */
    @Schema(description = "", name = "tableName")
    private String tableName;

    /**
     * null
     */
    @Schema(description = "null", name = "tableRows")
    private Long tableRows;

    /**
     * null
     */
    @Schema(description = "null", name = "tableType")
    private String tableType;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * null
     */
    @Schema(description = "null", name = "version")
    private String version;

}