package com.wu.framework.automation.platform.server.starter.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.util.Map;
import java.time.LocalDateTime;
import java.lang.Boolean;
/**
 * describe 自动化http节点动作 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "automation_node_http_action_command_dto",description = "自动化http节点动作")
public class AutomationNodeHttpActionDTO {


    /**
     * 
     * 自动化节点ID
     */
    @Schema(description ="自动化节点ID",name ="automationNodeId",example = "")
    private String automationNodeId;

    /**
     * 
     * 请求体
     */
    @Schema(description ="请求体",name ="body",example = "")
    private String body;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 请求头
     */
    @Schema(description ="请求头",name ="headers",example = "")
    private Map headers;

    /**
     * 
     * 请求方法
     */
    @Schema(description ="请求方法",name ="httpMethod",example = "")
    private String httpMethod;

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
     * 请求参数
     */
    @Schema(description ="请求参数",name ="params",example = "")
    private Map params;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 请求路径
     */
    @Schema(description ="请求路径",name ="url",example = "")
    private String url;

}