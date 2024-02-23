package com.wu.framework.sql.audit.platform.domain.model.sql.audit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain
 **/
@Data
@Accessors(chain = true)
@Schema(title = "sql_audit", description = "")
public class SqlAudit {


    /**
     * 执行sql使用的schema
     */
    @Schema(description = "执行sql使用的schema", name = "schema")
    @EasyExcelFiled(name = "schema")
    private String schema;

    /**
     * 应用名称
     */
    @Schema(description = "应用名称", name = "applicationName")
    @EasyExcelFiled(name = "应用名称")
    private String applicationName;

    /**
     * 执行sql使用的数据源
     */
    @Schema(description = "执行sql使用的数据源", name = "datasource")
    @EasyExcelFiled(name = "执行sql使用的数据源")
    private DataSource datasource;
    /**
     * 实例ID
     */
    @Schema(description = "实例ID", name = "instanceId")
    @EasyExcelFiled(name = "实例ID")
    private String instanceId;

    /**
     * 执行的sql
     */
    @Schema(description = "执行的sql", name = "executeSql")
    @EasyExcelFiled(name = "执行的sql")
    private String executeSql;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID", name = "id")
    @EasyExcelFiled(name = "主键ID")
    private String id;

    /**
     * 执行sql的ip
     */
    @Schema(description = "执行sql的ip", name = "ip")
    @EasyExcelFiled(name = "执行sql的ip")
    private String ip;

    /**
     * 请求ID
     */
    @Schema(description = "请求ID", name = "requestId")
    @EasyExcelFiled(name = "请求ID")
    private String requestId;

    /**
     * sql类型
     */
    @Schema(description = "sql类型", name = "sqlType")
    @EasyExcelFiled(name = "sql类型", dropdownOptions = {"查询", "修改", "新增", "删除"})
    private String sqlType;

    /**
     * 执行sql时间
     */
    @Schema(description = "执行sql时间", name = "time")
    @EasyExcelFiled(name = "执行sql时间")
    private Long time;

    @EasyExcelFiled(name = "操作的表")
    @Schema(description ="操作的表",name ="tableList",example = "")
    private List<String> tableList;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @EasyExcelFiled(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @EasyExcelFiled(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}