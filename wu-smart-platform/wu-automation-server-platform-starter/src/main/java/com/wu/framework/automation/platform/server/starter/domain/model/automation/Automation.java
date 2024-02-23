package com.wu.framework.automation.platform.server.starter.domain.model.automation;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationOneToManyField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "automation",description = "自动化")
public class Automation {


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
     * 下一次执行时间
     */
    @Schema(description ="下一次执行时间",name ="nextTime",example = "")
    private LocalDateTime nextTime;

    /**
     * 
     * 状态：启用停用
     */
    @Schema(description ="状态：启用停用",name ="status",example = "")
    private Boolean status;

    /**
     * 
     * 执行自动化时间间隔（没有则不执行）
     */
    @Schema(description ="执行自动化时间间隔（没有则不执行）",name ="timeInterval",example = "")
    private Integer timeInterval;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="name",example = "")
    private String name;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 执行自动化节点
     */
    @LazyTableTranslationOneToManyField(
            translationSourceName = "id",
            translationTargetName = "automationId",
            translationTargetTableName = "automation_node",
            translationTargetType = AutomationNode.class,
            type = LazyTranslationTableEndpoint.Type.ALL
    )
    private List<AutomationNode> automationNodeList;

}