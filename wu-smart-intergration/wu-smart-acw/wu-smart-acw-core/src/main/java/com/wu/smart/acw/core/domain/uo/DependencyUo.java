package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :依赖
 * @date : 2021/9/19 11:48 上午
 */
@Accessors(chain = true)
@Data
public class DependencyUo {
    @LazyTableFieldId
    private Long id;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "groupId", example = "top.wu2020")
    private String groupId;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "artifactId", example = "wu-database-lazy-starter")
    private String artifactId;

    @LazyTableFieldUnique
    @ApiModelProperty(value = "version", example = "1.0.6")
    private String version;

    @ApiModelProperty(value = "columnType", example = "pom")
    private String type;

    @ApiModelProperty(value = "scope", example = "compile")
    private String scope;

    @ApiModelProperty(value = "optional", example = "true")
    private boolean optional;
}
