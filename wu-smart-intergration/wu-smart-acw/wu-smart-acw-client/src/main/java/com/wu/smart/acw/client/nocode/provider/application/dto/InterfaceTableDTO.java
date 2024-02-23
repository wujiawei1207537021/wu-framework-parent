package com.wu.smart.acw.client.nocode.provider.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 接口与表的关联 
 *
 * @author Jia wei Wu
 * @date 2023/08/15 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "interface_table_command",description = "接口与表的关联")
public class InterfaceTableDTO {


    /**
     * 
     * api_id
     */
    @Schema(description ="api_id",name ="apiId",example = "")
    private Integer apiId;

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
    private Integer id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 是否主表
     */
    @Schema(description ="是否主表",name ="isMainTable",example = "")
    private Boolean isMainTable;

    /**
     * 
     * 表名称
     */
    @Schema(description ="表名称",name ="tableName",example = "")
    private String tableName;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}