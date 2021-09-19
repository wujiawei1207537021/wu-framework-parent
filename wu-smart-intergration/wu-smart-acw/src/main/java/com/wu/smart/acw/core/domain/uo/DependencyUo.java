package com.wu.smart.acw.core.domain.uo;

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
public class DependencyUo {
    private Long id;
    @ApiModelProperty(value = "groupId", example = "top.wu2020")
    private String groupId;
    @ApiModelProperty(value = "artifactId", example = "wu-database-lazy-starter")
    private String artifactId;
    @ApiModelProperty(value = "version", example = "1.0.5")
    private String version;
    @ApiModelProperty(value = "type", example = "pom")
    private String type;
    @ApiModelProperty(value = "scope", example = "compile")
    private String scope;
    @ApiModelProperty(value = "optional", example = "true")
    private boolean optional;
}
