package com.wu.framework.automation.platform.server.starter.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "automation_node_http_action",comment = "自动化http节点动作")
@Schema(title = "automation_node_http_action",description = "自动化http节点动作")
public class AutomationNodeHttpActionDO {


    /**
     * 
     * 自动化节点ID
     */
    @Schema(description ="自动化节点ID",name ="automationNodeId",example = "")
    @LazyTableField(name="automation_node_id",comment="自动化节点ID",columnType="varchar(255)")
    private String automationNodeId;

    /**
     * 
     * 请求体
     */
    @Schema(description ="请求体",name ="body",example = "")
    @LazyTableField(name="body",comment="请求体",columnType="text")
    private String body;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 
     * 请求头
     */
    @Schema(description ="请求头",name ="headers",example = "")
    @LazyTableField(name="headers",comment="请求头",columnType="json")
    private Map headers;

    /**
     * 
     * 请求方法
     */
    @Schema(description ="请求方法",name ="httpMethod",example = "")
    @LazyTableField(name="http_method",comment="请求方法",columnType="varchar(255)")
    private String httpMethod;

    /**
     *
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldId(name="id",comment="主键",idType = LazyTableFieldId.IdType.INPUT_ID)
    private String id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint",upsertStrategy = LazyFieldStrategy.NEVER)
    private Boolean isDeleted;

    /**
     * 
     * 请求参数
     */
    @Schema(description ="请求参数",name ="params",example = "")
    @LazyTableField(name="params",comment="请求参数",columnType="json")
    private Map params;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",columnType="datetime",extra="on update CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /**
     * 
     * 请求路径
     */
    @Schema(description ="请求路径",name ="url",example = "")
    @LazyTableField(name="url",comment="请求路径",columnType="varchar(255)")
    private String url;

}