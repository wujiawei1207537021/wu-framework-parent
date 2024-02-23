package com.wu.framework.automation.platform.server.starter.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "automation_node",comment = "自动化节点")
@Schema(title = "automation_node",description = "自动化节点")
public class AutomationNodeDO {

    /**
     *
     * 名称
     */
    @Schema(description ="null",name ="name",example = "")
    @LazyTableField(name="name",comment="名称",columnType="varchar(255)")
    private String name;

    /**
     * 
     * 动作类型：http 、other
     */
    @Schema(description ="动作类型：http 、other",name ="actionType",example = "")
    @LazyTableField(name="action_type",comment="动作类型：http 、other",columnType="varchar(255)")
    private String actionType;

    /**
     * 
     * 自动化ID
     */
    @Schema(description ="自动化ID",name ="automationId",example = "")
    @LazyTableField(name="automation_id",comment="自动化ID",columnType="varchar(255)")
    private String automationId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

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
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'", columnType = "tinyint", upsertStrategy = LazyFieldStrategy.NEVER)
    private Boolean isDeleted;

    /**
     * 
     * 状态：启用停用
     */
    @Schema(description ="状态：启用停用",name ="status",example = "")
    @LazyTableField(name="status",comment="状态：启用停用",columnType="tinyint")
    private Integer status;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @LazyTableField(name="sort",comment="排序")
    private Integer sort;
    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",columnType="datetime",extra="on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

}