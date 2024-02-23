package com.wu.framework.automation.platform.server.starter.application.command.automation.node;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
import java.lang.Boolean;
/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "automation_node_story_command",description = "自动化节点")
public class AutomationNodeStoryCommand {

    /**
     *
     * null
     */
    @Schema(description ="null",name ="name",example = "")
    private String name;
    /**
     * 
     * 动作类型：http 、other
     */
    @Schema(description ="动作类型：http 、other",name ="actionType",example = "")
    private String actionType;

    /**
     * 
     * 自动化ID
     */
    @Schema(description ="自动化ID",name ="automationId",example = "")
    private String automationId;


    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private String id;

    /**
     * 
     * 状态：启用停用
     */
    @Schema(description ="状态：启用停用",name ="status",example = "")
    private Integer status;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;


}