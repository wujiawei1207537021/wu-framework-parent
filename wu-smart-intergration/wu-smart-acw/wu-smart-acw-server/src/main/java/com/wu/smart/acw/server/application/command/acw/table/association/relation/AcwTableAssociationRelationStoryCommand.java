package com.wu.smart.acw.server.application.command.acw.table.association.relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 表关联关系 
 *
 * @author Jia wei Wu
 * @date 2023/11/01 10:16 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "acw_table_association_relation_story_command",description = "表关联关系")
public class AcwTableAssociationRelationStoryCommand {


    /**
     * 
     * 数据库实例schema
     */
    @Schema(description ="数据库实例schema",name ="schema",example = "")
    private String schema;

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
    private String id;

    /**
     * 
     * 数据库实例ID
     */
    @Schema(description ="数据库实例ID",name ="instanceId",example = "")
    private String instanceId;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 关系，最大是100
     */
    @Schema(description ="关系，最大是100",name ="relation",example = "")
    private double relation;

    /**
     * 
     * 关系表
     */
    @Schema(description ="关系表",name ="relationTable",example = "")
    private String relationTable;

    /**
     * 
     * 关系表字段
     */
    @Schema(description ="关系表字段",name ="relationTableColumn",example = "")
    private String relationTableColumn;

    /**
     * 
     * 原始表字段
     */
    @Schema(description ="原始表字段",name ="sourceTableColumn",example = "")
    private String sourceTableColumn;

    /**
     * 
     * 原始表
     */
    @Schema(description ="原始表",name ="sourceTable",example = "")
    private String sourceTable;

    /**
     * 
     * 类型(1 自动产生的、2 手动添加的)
     */
    @Schema(description ="类型(1 自动产生的、2 手动添加的)",name ="type",example = "")
    private Integer type;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}