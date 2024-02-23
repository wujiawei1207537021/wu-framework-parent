package com.wu.smart.acw.server.application.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 数据库表信息
 *
 * @author Jia wei Wu
 * @date 2023/06/07 11:07 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "table_command", description = "数据库表信息")
public class AcwTableCommand {


    /**
     * 主键
     */
    private Long id;

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

    private String tableCatalog;
    /**
     * 数据库
     */
    @Schema(description = "数据库")
    private String schemaName;

    /**
     * 数据库ID
     */
    @Schema(description = "数据库ID")
    private Long schemaNameId;

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;
    private String tableType;
    private String engine;
    private String version;
    private String rowFormat;
    private Long tableRows;
    private String avgRowLength;
    private String dataLength;
    private String maxDataLength;
    private String indexLength;
    private String dataFree;
    private String autoIncrement;
    private String checkTime;
    private String tableCollation;
    private String checksum;
    private String createOptions;
    private String tableComment;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted = false;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 初始化数据库到本地
     */
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal = false;


    /**
     * 表字段
     */
    private List<AcwTableColumnCommand> tableColumnList;

    /**
     * 字段索引
     */
    private List<AcwTableColumnIndexCommand> tableColumnIndexList;

}