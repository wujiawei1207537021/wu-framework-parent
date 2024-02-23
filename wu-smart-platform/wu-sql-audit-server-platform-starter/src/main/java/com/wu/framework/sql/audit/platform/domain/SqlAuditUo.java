package com.wu.framework.sql.audit.platform.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * description sql审计 对象
 *
 * @author 吴佳伟
 * @date 2023/05/12 11:25
 */
@Data
@LazyTable(comment = "sql审计")
public class SqlAuditUo {


    @LazyTableFieldId(comment = "主键ID")
    private Long id;

    @LazyTableField(comment = "应用名称")
    private String applicationName;

    @LazyTableField(comment = "sql类型")
    private String sqlType;

    @LazyTableField(comment = "执行的sql",columnType = "longtext")
    private String executeSql;


    @LazyTableField(comment = "执行sql的ip")
    private String ip;

    @LazyTableField(comment = "请求ID")
    private String requestId;

    @LazyTableField(comment = "执行sql时间")
    private long time;

    @LazyTableField(comment = "执行sql使用的数据源",columnType = "Blob")
    private DataSource datasource;

    @LazyTableField(comment = "执行sql使用的schema")
    private String schema;

    /**
     *
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;

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
