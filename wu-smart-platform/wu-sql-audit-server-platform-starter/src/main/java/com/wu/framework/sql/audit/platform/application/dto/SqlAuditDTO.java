package com.wu.framework.sql.audit.platform.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;
import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/24 09:53 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO
 **/
@Data
@Accessors(chain = true)
@Schema(title = "sql_audit_command",description = "")
public class SqlAuditDTO {


    /**
     *
     * 执行sql使用的schema
     */
    @Schema(description ="执行sql使用的schema",name ="schema",example = "")
    private String schema;

    /**
     *
     * 应用名称
     */
    @Schema(description ="应用名称",name ="applicationName",example = "")
    private String applicationName;

    /**
     *
     * 执行sql使用的数据源
     */
    @Schema(description ="执行sql使用的数据源",name ="datasource",example = "")
    private DataSource datasource;

    /**
     *
     * 执行的sql
     */
    @Schema(description ="执行的sql",name ="executeSql",example = "")
    private String executeSql;
    /**
     *
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    private String instanceId;

    /**
     *
     * 主键ID
     */
    @Schema(description ="主键ID",name ="id",example = "")
    private String id;

    /**
     *
     * 执行sql的ip
     */
    @Schema(description ="执行sql的ip",name ="ip",example = "")
    private String ip;

    /**
     *
     * 请求ID
     */
    @Schema(description ="请求ID",name ="requestId",example = "")
    private String requestId;

    private List<String> tableList;
    /**
     *
     * sql类型
     */
    @Schema(description ="sql类型",name ="sqlType",example = "")
    private String sqlType;

    /**
     *
     * 执行sql时间
     */
    @Schema(description ="执行sql时间",name ="time",example = "")
    private Long time;

}
