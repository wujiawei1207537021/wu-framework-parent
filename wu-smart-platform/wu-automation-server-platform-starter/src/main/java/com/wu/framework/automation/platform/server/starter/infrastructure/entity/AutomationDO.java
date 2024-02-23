package com.wu.framework.automation.platform.server.starter.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.String;
import java.lang.Boolean;
/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "automation",comment = "自动化")
@Schema(title = "automation",description = "自动化")
public class AutomationDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER)
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
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint",upsertStrategy = LazyFieldStrategy.NEVER)
    private Boolean isDeleted;

    /**
     * 
     * 下一次执行时间
     */
    @Schema(description ="下一次执行时间",name ="nextTime",example = "")
    @LazyTableField(name="next_time",comment="下一次执行时间",columnType="datetime")
    private LocalDateTime nextTime;

    /**
     * 
     * 状态：启用停用
     */
    @Schema(description ="状态：启用停用",name ="status",example = "")
    @LazyTableField(name="status",comment="状态：启用停用",columnType="tinyint")
    private Boolean status;

    /**
     * 
     * 执行自动化时间间隔（没有则不执行）
     */
    @Schema(description ="执行自动化时间间隔（没有则不执行）",name ="timeInterval",example = "")
    @LazyTableField(name="time_interval",comment="执行自动化时间间隔（没有则不执行）",columnType="int")
    private Integer timeInterval;

    /**
     * 
     * 名称
     */
    @Schema(description ="null",name ="name",example = "")
    @LazyTableField(name="name",comment="名称",columnType="varchar(255)")
    private String name;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",columnType="datetime",extra="on update CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

}