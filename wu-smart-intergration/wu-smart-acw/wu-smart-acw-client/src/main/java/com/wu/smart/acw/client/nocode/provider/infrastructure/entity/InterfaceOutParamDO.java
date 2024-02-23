package com.wu.smart.acw.client.nocode.provider.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface_out_param",comment = "接口输出参数")
@Schema(title = "interface_out_param",description = "接口输出参数")
public class InterfaceOutParamDO {

    /**
     *
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableField(name="id",comment="主键",columnType="varchar(255)",upsertStrategy = LazyFieldStrategy.NEVER)
    private String id;

    /**
     *
     * 输出参数名称 如：age
     */
    @Schema(description ="输出参数名称 如：age",name ="name",example = "")
    @LazyTableFieldUnique(name="name",comment="输出参数名称 如：age",columnType="varchar(255)")
    private String name;

    /**
     *
     * 输出参数映射名称 如：age  默认等于name
     */
    @Schema(description ="输出参数映射名称 如：age  默认等于name",name ="mappingName",example = "")
    @LazyTableFieldUnique(name="mapping_name",comment="输出参数映射名称 如：age  默认等于name",columnType="varchar(255)")
    private String mappingName;

    /**
     * 
     * 接口ID
     */
    @Schema(description ="接口ID",name ="apiId",example = "")
    @LazyTableFieldUnique(name = "api_id", comment = "接口ID")
    private Integer apiId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra="")
    private LocalDateTime createTime;


    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;
    /**
     *
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;
}