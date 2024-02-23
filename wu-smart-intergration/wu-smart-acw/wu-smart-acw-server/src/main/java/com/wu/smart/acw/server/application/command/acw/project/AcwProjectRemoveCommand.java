package com.wu.smart.acw.server.application.command.acw.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * describe ACW项目 
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_project_remove_command",description = "ACW项目")
public class AcwProjectRemoveCommand {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="acwDependencyUoList",example = "")
    private Map acwDependencyUoList;

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
    @Schema(description ="",name ="id",example = "")
    private Long id;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="instanceId",example = "")
    private String instanceId;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="ormFrameEnums",example = "")
    private String ormFrameEnums;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="owner",example = "")
    private String owner;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="projectName",example = "")
    private String projectName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="uiFrameEnums",example = "")
    private String uiFrameEnums;

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