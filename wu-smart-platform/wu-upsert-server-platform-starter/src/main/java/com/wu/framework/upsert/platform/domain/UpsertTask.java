package com.wu.framework.upsert.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * upsert 任务
 *
 * @author wujiawei
 */
@Data
@Accessors(chain = true)
public class UpsertTask {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id", example = "")
    @LazyTableField(name = "id", comment = "主键")
    private Long id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", name = "taskName", example = "")
    @LazyTableField(name = "task_name", comment = "任务名称")
    private String taskName;

    /**
     * 任务类型
     */
    @ApiModelProperty(value = "任务类型", name = "type", example = "")
    @LazyTableField(name = "type", comment = "任务类型")
    private String type;


    /**
     * 任务配置
     */
    @ApiModelProperty(value = "任务配置", name = "config", example = "")
    @LazyTableField(name = "config", comment = "任务配置")
    private String config;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", name = "stats", example = "")
    @LazyTableField(name = "stats", comment = "任务状态")
    private String stats;

}
