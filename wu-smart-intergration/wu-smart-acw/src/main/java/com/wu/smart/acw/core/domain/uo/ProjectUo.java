package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableFieldUnique;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author : 吴佳伟
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
    @ApiModelProperty(hidden = true)
    private Long id;
    /**
     * 项目名称
     */
    @LazyTableFieldUnique
    @ApiModelProperty(value = "项目名称",example = "ace-project")
    private String projectName;
    /**
     * 版本
     */
    @LazyTableFieldUnique
    @ApiModelProperty(value = "版本",example = "ace-1.0.0")
    private String version;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "拥有者",example = "wujiawei")
    private String owner;
    /**
     * 依赖
     */
    @ApiModelProperty(value = "依赖",dataType = "DependencyUo")
    private List<DependencyUo> dependencyUoList;
}
