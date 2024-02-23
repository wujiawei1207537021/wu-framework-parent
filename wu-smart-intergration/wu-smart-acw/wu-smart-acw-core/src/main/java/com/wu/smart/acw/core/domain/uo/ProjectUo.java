package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import com.wu.smart.acw.core.domain.enums.OrmFrameEnums;
import com.wu.smart.acw.core.domain.enums.UIFrameEnums;
import io.swagger.annotations.ApiModelProperty;
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
@LazyTable(tableName = "project")
public class ProjectUo {
    /**
     * 主键
     */
    @LazyTableFieldId
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 数据库服务器ID
     */
    @ApiModelProperty(value = "数据库服务器ID")
    private Long databaseServerId;

    /**
     * 数据库名称
     */
    @LazyTableField(name = "schema")
    @ApiModelProperty(required = true, value = "数据库名称")
    private String schema;
    /**
     * 项目名称
     */
    @LazyTableFieldUnique
    @ApiModelProperty(value = "项目名称", example = "acw-project")
    private String projectName;
    /**
     * 版本
     */
    @LazyTableFieldUnique
    @ApiModelProperty(value = "版本", example = "acw-1.0.0")
    private String version;

    /**
     * 拥有者
     */
    @LazyTableFieldUnique
    @ApiModelProperty(value = "拥有者", example = "wujiawei")
    private String owner;
    /**
     * 依赖
     */
    @ApiModelProperty(value = "依赖", dataType = "DependencyUo")
    private List<DependencyUo> dependencyUoList;

    /**
     * orm 框架
     */
    private OrmFrameEnums ormFrameEnums = OrmFrameEnums.UPSERT;

    /**
     * UI 框架
     */
    private UIFrameEnums uiFrameEnums = UIFrameEnums.VUE;

}
