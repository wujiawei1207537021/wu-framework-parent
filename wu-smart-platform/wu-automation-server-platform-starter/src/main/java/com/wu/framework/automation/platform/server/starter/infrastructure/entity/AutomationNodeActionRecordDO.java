package com.wu.framework.automation.platform.server.starter.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
import java.lang.Boolean;
/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "automation_node_action_record",comment = "自动化记录")
@Schema(title = "automation_node_action_record",description = "自动化记录")
public class AutomationNodeActionRecordDO {


    /**
     * 
     * 自动化节点执行ID
     */
    @Schema(description ="自动化节点执行ID",name ="automationNodeActionId",example = "")
    @LazyTableField(name="automation_node_action_id",comment="自动化节点执行ID",columnType="varchar(255)")
    private String automationNodeActionId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     *
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableField(name="id",comment="主键",notNull=true,key=true,columnType="varchar(255)")
    private String id;
    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint")
    private Boolean isDeleted;

    /**
     * 
     * 节点执行结果
     */
    @Schema(description ="节点执行结果",name ="result",example = "")
    @LazyTableField(name="result",comment="节点执行结果",columnType="varchar(255)")
    private String result;

    /**
     * 
     * 节点执行状态
     */
    @Schema(description ="节点执行状态",name ="status",example = "")
    @LazyTableField(name="status",comment="节点执行状态",columnType="tinyint")
    private Boolean status;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",columnType="datetime",extra="on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}