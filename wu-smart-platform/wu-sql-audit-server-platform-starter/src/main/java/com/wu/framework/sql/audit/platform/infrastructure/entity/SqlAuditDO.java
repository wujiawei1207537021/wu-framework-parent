package com.wu.framework.sql.audit.platform.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "sql_audit",comment = "sql审计数据")
@Schema(title = "sql_audit",description = "")
public class SqlAuditDO {


    /**
     * 
     * 执行sql使用的schema
     */
    @Schema(description ="执行sql使用的schema",name ="schema",example = "")
    @LazyTableField(name="schema",comment="执行sql使用的schema",columnType="varchar(255)")
    private String schema;

    /**
     * 
     * 应用名称
     */
    @Schema(description ="应用名称",name ="applicationName",example = "")
    @LazyTableField(name="application_name",comment="应用名称",columnType="varchar(255)")
    private String applicationName;

    /**
     * 
     * 执行sql使用的数据源
     */
    @Schema(description ="执行sql使用的数据源",name ="datasource",example = "")
    @LazyTableField(name="datasource",comment="执行sql使用的数据源",columnType="blob",exist = false)
    private DataSource datasource;
    /**
     *
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    @LazyTableField(name="instance_id",comment="实例ID",columnType="varchar(255)")
    private String instanceId;

    /**
     * 
     * 执行的sql
     */
    @Schema(description ="执行的sql",name ="executeSql",example = "")
    @LazyTableField(name="execute_sql",comment="执行的sql",columnType="longtext")
    private String executeSql;

    /**
     * 
     * 主键ID
     */
    @Schema(description ="主键ID",name ="id",example = "")
    @LazyTableFieldUUId(name = "id", comment = "主键ID")
    private String id;

    /**
     * 
     * 执行sql的ip
     */
    @Schema(description ="执行sql的ip",name ="ip",example = "")
    @LazyTableField(name="ip",comment="执行sql的ip",columnType="varchar(255)")
    private String ip;

    /**
     * 
     * 请求ID
     */
    @Schema(description ="请求ID",name ="requestId",example = "")
    @LazyTableField(name="request_id",comment="请求ID",columnType="varchar(255)")
    private String requestId;

    /**
     * 
     * sql类型
     */
    @Schema(description ="sql类型",name ="sqlType",example = "")
    @LazyTableField(name="sql_type",comment="sql类型",columnType="varchar(255)")
    private String sqlType;

    /**
     * 
     * 执行sql时间
     */
    @Deprecated
    @Schema(description ="执行sql时间",name ="time",example = "")
    @LazyTableField(name="time",comment="执行sql时间",columnType="bigint")
    private Long time;


    /**
     * 操作的表
     */
    @Schema(description ="操作的表",name ="tableList",example = "")
    @LazyTableField(comment="操作的表",columnType="text")
    private List<String> tableList;


    /**
     *
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     *
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}