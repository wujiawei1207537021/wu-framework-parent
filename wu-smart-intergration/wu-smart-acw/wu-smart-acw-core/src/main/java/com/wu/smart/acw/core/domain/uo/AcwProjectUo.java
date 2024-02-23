package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.smart.acw.core.domain.enums.OrmFrameEnums;
import com.wu.smart.acw.core.domain.enums.UIFrameEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/9/19 11:42 上午
 */
@Accessors(chain = true)
@Data
@LazyTable(comment = "ACW项目")
public class AcwProjectUo {
    /**
     * 主键
     */
    @LazyTableFieldId
    @Schema(hidden = true, example = "1")
    private Long id;

    /**
     * 数据库服务器ID
     */
    @Schema(description = "数据库服务器ID", example = "1")
    private String instanceId;

    /**
     * 项目名称
     */
    @LazyTableFieldUnique
    @Schema(description = "项目名称", example = "acw-project")
    private String projectName;
    /**
     * 版本
     */
    @LazyTableFieldUnique
    @Schema(description = "版本", example = "acw-1.0.0")
    private String version;

    /**
     * 拥有者
     */
    @LazyTableFieldUnique
    @Schema(description = "拥有者", example = "wujiawei")
    private String owner;
    /**
     * 依赖
     */
    @Schema(description = "依赖")
    private List<AcwDependencyUo> acwDependencyUoList;

    /**
     * orm 框架
     */
    private OrmFrameEnums ormFrameEnums;

    /**
     * UI 框架
     */
    private UIFrameEnums uiFrameEnums;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'")
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime", example = "2022-01-1 20:20:19")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime", example = "2022-01-1 20:20:19")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}
