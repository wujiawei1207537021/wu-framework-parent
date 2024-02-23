package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe : 数据库库信息
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/6 21:48
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "ACW 数据库库信息")
public class AcwSchemaUo {


    /**
     * 数据库实例ID
     */
    @Schema(description = "数据库实例ID", name = "instanceId")
    @LazyTableFieldUnique(name = "instance_id", comment = "数据库实例ID", notNull = true)
    private String instanceId;
    /**
     * 数据库名称
     */
    @Schema(description = "null", name = "schemaName")
    @LazyTableFieldUnique(name = "schema_name", comment = "数据库名称", alias = "schemaName")
    private String schemaName;
    /**
     * null
     */
    @Schema(description = "null", name = "characterSet")
    @LazyTableField(name = "character_set", comment = "null")
    private String characterSet;
    /**
     * null
     */
    @Schema(description = "null", name = "sortingRules")
    @LazyTableField(name = "sorting_rules", comment = "null")
    private String sortingRules;
    /**
     * null
     */
    @Schema(description = "null", name = "ext")
    @LazyTableField(name = "ext", comment = "null")
    private String ext;
    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键", upsertStrategy = LazyFieldStrategy.NEVER)
    private Long id;
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
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;
    /**
     * 初始化数据库到本地
     */
    @LazyTableField(comment = "初始化数据库到本地")
    @Schema(description = "initializeToLocal", example = "1")
    private Boolean initializeToLocal;
}