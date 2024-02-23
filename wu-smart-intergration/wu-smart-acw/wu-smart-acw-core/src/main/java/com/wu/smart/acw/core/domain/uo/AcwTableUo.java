package com.wu.smart.acw.core.domain.uo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe: 数据库表信息
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/5/6 22:29
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "数据库表信息")
@Deprecated
public class AcwTableUo {


    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;

    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableFieldUnique(name = "instance_id", comment = "数据库实例ID")
    private String instanceId;
    /**
     * 数据库实例名称
     */
    @Schema(description = "数据库实例名称", name = "instanceName")
    @LazyTableField(name = "instance_name", comment = "数据库实例ID")
    private String instanceName;

    private String tableCatalog;
    /**
     * 数据库
     */
    @Schema(description = "数据库")
    @LazyTableField
    private String schemaName;

    /**
     * 数据库ID
     */
    @Schema(description = "数据库ID")
    @LazyTableFieldUnique
    private Long schemaNameId;

    /**
     * 表名
     */
    @Schema(description = "表名")
    @LazyTableFieldUnique
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
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'")
    private Boolean isDeleted = false;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 初始化数据库到本地
     */
    @LazyTableField(comment = "初始化数据库到本地")
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal = false;

}