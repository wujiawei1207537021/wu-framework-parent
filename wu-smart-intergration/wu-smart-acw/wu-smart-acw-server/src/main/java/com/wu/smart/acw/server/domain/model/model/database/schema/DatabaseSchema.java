package com.wu.smart.acw.server.domain.model.model.database.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "database_schema",description = "")
public class DatabaseSchema {


    /**
     * 
     * null
     */
    @Schema(description ="null",name ="characterSet",example = "")
    private String characterSet;

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
     * 数据库名称
     */
    @Schema(description ="数据库名称",name ="databaseSchemaName",example = "")
    private String databaseSchemaName;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="ext",example = "")
    private String ext;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 初始化数据库到本地
     */
    @Schema(description ="初始化数据库到本地",name ="initializeToLocal",example = "")
    private Boolean initializeToLocal;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="sortingRules",example = "")
    private String sortingRules;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}