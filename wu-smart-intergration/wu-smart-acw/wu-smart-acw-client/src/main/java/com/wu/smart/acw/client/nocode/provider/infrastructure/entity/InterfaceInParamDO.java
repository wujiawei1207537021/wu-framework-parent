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
 * describe 接口输入参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface_in_param",comment = "接口输入参数")
@Schema(title = "interface_in_param",description = "接口输入参数")
public class InterfaceInParamDO {
    /**
     *
     * 参数名称 如：age
     */
    @Schema(description ="参数名称 如：age",name ="name",example = "")
    @LazyTableFieldUnique(name="name",comment="参数名称 如：age",columnType="varchar(255)")
    private String name;

    @Schema(description ="参数数据类型",name ="dataType",example = "")
    @LazyTableField(name="dataType",comment="参数数据类型 如：varchar(255)",columnType="varchar(255)")
    private String dataType;
    /**
     * 
     * 参数条件 大于、等于、小于
     */
    @Schema(description ="参数条件 大于、等于、小于",name ="term",example = "")
    @LazyTableFieldUnique(name="condition",comment="参数条件 大于、等于、小于",columnType="varchar(255)")
    private String term;



    /**
     * 参数类型 路径参数、请求参数、请求体参数
     */
    @Schema(description ="参数类型 路径参数、请求参数、请求体参数",name ="type",example = "")
    @LazyTableFieldUnique(name="type",comment="参数类型 路径参数、请求参数、请求体参数",columnType="varchar(255)")
    private String type;
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
     * 参数默认值 参数默认值类型  1:静态参数 如 1，2，3，4，5，6，7，8，9  2:动态参数 如 ${age} 3:关联参数 如user.name
     */
    @Schema(description ="参数默认值 参数默认值类型  1:静态参数 如 1，2，3，4，5，6，7，8，9  2:动态参数 如 ${age} 3:关联参数 如user.name",name ="defaultValue",example = "")
    @LazyTableField(name="default_value",comment="参数默认值 参数默认值类型  1:静态参数 如 1，2，3，4，5，6，7，8，9  2:动态参数 如 ${age} 3:关联参数 如user.name",columnType="varchar(255)")
    private String defaultValue;

    /**
     * 
     * 参数默认值类型 1:静态参数，2:动态参数 3:关联参数
     */
    @Schema(description ="参数默认值类型 1:静态参数，2:动态参数 3:关联参数",name ="defaultValueType",example = "")
    @LazyTableField(name="default_value_type",comment="参数默认值类型 1:静态参数，2:动态参数 3:关联参数",columnType="int")
    private Integer defaultValueType;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableField(name="id",comment="主键",columnType="varchar(255)",upsertStrategy = LazyFieldStrategy.NEVER)
    private String id;



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