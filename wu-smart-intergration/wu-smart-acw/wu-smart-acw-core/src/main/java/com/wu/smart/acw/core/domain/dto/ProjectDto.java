package com.wu.smart.acw.core.domain.dto;


import com.wu.smart.acw.core.domain.enums.OrmFrameEnums;
import com.wu.smart.acw.core.domain.enums.UIFrameEnums;
import com.wu.smart.acw.core.domain.uo.AcwDependencyUo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/9/19 11:42 上午
 */
@Accessors(chain = true)
@Data
public class ProjectDto {
    /**
     * 主键
     */
    @Schema(hidden = true)
    private String id;

    /**
     * 数据库服务器ID
     */
    @Schema(description = "数据库服务器ID")
    private String instanceId;

    /**
     * 数据库实例
     */
    @Schema(description = "数据库实例")
    private String instanceName;

    /**
     * 项目名称
     */
    @Schema(description = "项目名称", example = "acw-project")
    private String projectName;
    /**
     * 版本
     */
    @Schema(description = "版本", example = "acw-1.0.0")
    private String version;

    /**
     * 拥有者
     */
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
    private OrmFrameEnums ormFrameEnums = OrmFrameEnums.UPSERT;

    /**
     * UI 框架
     */
    private UIFrameEnums uiFrameEnums = UIFrameEnums.VUE;

}
