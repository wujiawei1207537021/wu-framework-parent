package com.wu.smart.acw.server.application.command.acw.dependency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 依赖 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_dependency_story_command",description = "依赖")
public class AcwDependencyStoryCommand {


    /**
     * 
     * 
     */
    @Schema(description ="",name ="artifactId",example = "")
    private String artifactId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="groupId",example = "")
    private String groupId;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="optional",example = "")
    private Boolean optional;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="scope",example = "")
    private String scope;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="type",example = "")
    private String type;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="version",example = "")
    private String version;

}