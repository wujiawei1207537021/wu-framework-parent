package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableFieldUnique;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : 吴佳伟
 * @version 1.0
 * describe :依赖
 * @date : 2021/9/19 11:48 上午
 */
@Accessors(chain = true)
@Data
@LazyTable(tableName = "dependency")
public class DependencyUo {

    private Long id;
    
    @LazyTableFieldUnique
    @ApiModelProperty(value = "groupId", example = "top.wu2020")
    private String groupId;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "artifactId", example = "wu-database-lazy-starter")
    private String artifactId;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "version", example = "1.0.5")
    private String version;

    @ApiModelProperty(value = "type", example = "pom")
    private String type;

    @ApiModelProperty(value = "scope", example = "compile")
    private String scope;

    @ApiModelProperty(value = "optional", example = "true")
    private boolean optional;
}
