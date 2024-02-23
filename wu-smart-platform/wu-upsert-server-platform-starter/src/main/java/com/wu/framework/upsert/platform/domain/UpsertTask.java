package com.wu.framework.upsert.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * upsert 任务
 *
 * @author Jia wei Wu
 */
@Data
@Accessors(chain = true)
public class UpsertTask {

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableField(name = "id", comment = "主键")
    private Long id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", name = "taskName")
    @LazyTableField(name = "task_name", comment = "任务名称")
    private String taskName;

    /**
     * 任务类型
     */
    @Schema(description = "任务类型", name = "type")
    @LazyTableField(name = "type", comment = "任务类型")
    private String type;


    /**
     * 任务配置
     */
    @Schema(description = "任务配置", name = "config")
    @LazyTableField(name = "config", comment = "任务配置")
    private String config;

    /**
     * 任务状态
     */
    @Schema(description = "任务状态", name = "stats")
    @LazyTableField(name = "stats", comment = "任务状态")
    private String stats;
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
