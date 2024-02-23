package com.wu.framework.dynamic.iframe.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "dynamic_sql_window",comment = "动态SQL窗口")
public class DynamicSQLWindowDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     *
     * 实例ID
     */
    @LazyTableField(comment="实例ID")
    private String instanceId;

    /**
     * 
     * 动态sql
     */
    @LazyTableField(comment="动态sql")
    private String dynamicSql;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     * 
     * 是否站内
     */
    @Schema(description ="是否站内",name ="isStation",example = "")
    @LazyTableField(name="is_station",comment="是否站内",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isStation;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;


}