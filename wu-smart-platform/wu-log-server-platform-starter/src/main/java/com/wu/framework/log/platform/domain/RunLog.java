package com.wu.framework.log.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldCreateTime;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUpdateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe : 运行日志
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/12/29 21:31
 */
@Data
@LazyTable(comment = "运行日志")
public class RunLog {
    /**
     * id
     */
    @Schema(description = "id", name = "id")
    @LazyTableField(name = "id", comment = "id")
    private Long id;
    /**
     * 类型（异常、警告、信息）
     */
    @Schema(description = "类型（异常、警告、信息）", name = "type")
    @LazyTableField(name = "type", comment = "类型（异常、警告、信息）")
    private String type;
    /**
     * 运行环境
     */
    @Schema(description = "操作系统", name = "os")
    @LazyTableField(name = "os", comment = "操作系统")
    private String os;
    /**
     * 描述
     */
    @Schema(description = "描述", name = "description")
    @LazyTableField(name = "description", comment = "描述")
    private String description;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;
}
