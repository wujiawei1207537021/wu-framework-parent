package com.wu.framework.automation.platform.server.starter.domain.model.automation.node;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationOneField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "automation_node",description = "自动化节点")
public class AutomationNode {


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
     * 状态：启用停用
     */
    @Schema(description ="状态：启用停用",name ="status",example = "")
    private Integer status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 节点执行信息
     */
    @LazyTableTranslationOneField(
            translationSourceName = "id",
            translationTargetName = "automationNodeId",
            translationTargetTableName = "automation_node_http_action",
            translationTargetType = AutomationNodeHttpAction.class,
            type = LazyTranslationTableEndpoint.Type.ALL
    )
    private AutomationNodeHttpAction automationNodeHttpAction;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

}