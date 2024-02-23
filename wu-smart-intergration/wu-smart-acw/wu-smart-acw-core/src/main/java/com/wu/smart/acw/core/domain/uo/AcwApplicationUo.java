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
 * describe : 应用
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/5/30 00:15
 */
@Data
@Accessors(chain = true)
@LazyTable(comment = "应用")
public class AcwApplicationUo {

    /**
     * 主键
     */
    @LazyTableFieldId(name = "application_id")
    @Schema(description = "主键")
    private Long applicationId;
    /**
     * 项目ID
     */
    @LazyTableField(name = "project_id")
    @Schema(description = "项目ID")
    private Long projectId;
    /**
     * 应用名称
     */
    @LazyTableField(name = "application_name")
    @Schema(description = "应用名称")
    private String applicationName;


    /**
     * 数据库Schema
     */
//    @LazyFieldNe(ignoreEmpty = false)
    @LazyTableFieldUnique
    @Schema(description = "数据库Schema")
    private String schemaName;

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
}
