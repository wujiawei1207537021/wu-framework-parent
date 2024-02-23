package com.wu.smart.acw.server.domain.model.model.database.table.auto.stuffed.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 数据库表填充记录 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "database_table_auto_stuffed_record",description = "数据库表填充记录")
public class DatabaseTableAutoStuffedRecord {


    /**
     * 
     * 自动填充数量
     */
    @Schema(description ="自动填充数量",name ="autoStuffedNum",example = "")
    private Long autoStuffedNum;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 数据库实例ID
     */
    @Schema(description ="数据库实例ID",name ="databaseInstanceId",example = "")
    private String databaseInstanceId;

    /**
     * 
     * 数据库实例名称
     */
    @Schema(description ="数据库实例名称",name ="databaseInstanceName",example = "")
    private String databaseInstanceName;

    /**
     * 
     * 数据库schema ID
     */
    @Schema(description ="数据库schema ID",name ="databaseSchemaId",example = "")
    private Long databaseSchemaId;

    /**
     * 
     * 数据库schema名称
     */
    @Schema(description ="数据库schema名称",name ="databaseSchemaName",example = "")
    private String databaseSchemaName;

    /**
     * 
     * 自动填充数据记录ID
     */
    @Schema(description ="自动填充数据记录ID",name ="id",example = "")
    private String id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 自动填充状态
     */
    @Schema(description ="自动填充状态",name ="status",example = "")
    private Boolean status;

    /**
     * 
     * 自动填充表
     */
    @Schema(description ="自动填充表",name ="tableName",example = "")
    private String tableName;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}