package com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
import java.lang.Boolean;
/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "automation_node_action_record_remove_command",description = "自动化记录")
public class AutomationNodeActionRecordRemoveCommand {


    /**
     * 
     * 自动化节点执行ID
     */
    @Schema(description ="自动化节点执行ID",name ="automationNodeActionId",example = "")
    private String automationNodeActionId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private String id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 节点执行结果
     */
    @Schema(description ="节点执行结果",name ="result",example = "")
    private String result;

    /**
     * 
     * 节点执行状态
     */
    @Schema(description ="节点执行状态",name ="status",example = "")
    private Boolean status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}