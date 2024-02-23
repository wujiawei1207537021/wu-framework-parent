package com.wu.smart.acw.client.nocode.provider.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe Dataway 中的API
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface_info", comment = "Dataway 中的API")
@Schema(title = "interface_info", description = "Dataway 中的API")
public class InterfaceInfoDO {


    /**
     * 注释
     */
    @Schema(description = "注释", name = "apiComment")
    @LazyTableField(name = "api_comment", comment = "注释", columnType = "varchar(255)")
    private String apiComment;

    /**
     * 分组
     */
    @Schema(description = "分组", name = "tag", example = "分组")
    @LazyTableField(name = "tag", comment = "注释", columnType = "varchar(255)")
    private String tag;
    /**
     * 使用的数据库
     */
    @Schema(description = "使用的数据库", name = "schema", example = "使用的数据库")
    @LazyTableField(name = "schema", comment = "使用的数据库", columnType = "varchar(255)")
    private String schema;

    /**
     * ID
     */
    @Schema(description = "ID", name = "apiId")
    @LazyTableFieldId(name = "api_id", comment = "ID",upsertStrategy = LazyFieldStrategy.NEVER)
    private Integer apiId;

    /**
     * HttpMethod：GET、PUT、POST
     */
    @Schema(description = "HttpMethod：GET、PUT、POST", name = "apiMethod")
    @LazyTableFieldUnique(name = "api_method", comment = "HttpMethod：GET、PUT、POST", notNull = true, columnType = "varchar(12)")
    private String apiMethod;

    /**
     * 拦截路径
     */
    @Schema(description = "拦截路径", name = "apiPath")
    @LazyTableFieldUnique(name = "api_path", comment = "拦截路径", notNull = true, columnType = "varchar(512)")
    private String apiPath;

    /**
     * api返回结果类型 0单个对象，1集合对象，2分页对象
     */
    @Schema(description = "api返回结果类型 0单个对象，1集合对象，2分页对象", name = "apiResultType")
    @LazyTableField(name = "api_result_type", comment = "api返回结果类型 0单个对象，1集合对象，2分页对象", notNull = true, columnType = "int")
    private Integer apiResultType;

    /**
     * 状态：0草稿，1发布，2有变更，3禁用
     */
    @Schema(description = "状态：0草稿，1发布，2有变更，3禁用", name = "apiStatus")
    @LazyTableField(name = "api_status", comment = "状态：0草稿，1发布，2有变更，3禁用", notNull = true, columnType = "int")
    private Integer apiStatus;

    /**
     * 脚本类型：SQL、DataQL
     */
    @Schema(description = "脚本类型：SQL、DataQL", name = "apiType")
    @LazyTableField(name = "api_type", comment = "脚本类型：SQL、DataQL", notNull = true, columnType = "varchar(24)")
    private String apiType;

    /**
     * 执行类型: select、update、delete、insert、upsert
     */
    @Schema(description = "执行类型: select、update、delete、insert、upsert", name = "executeType")
    @LazyTableField(name = "execute_type", comment = "执行类型: select、update、delete、insert、upsert", notNull = true, columnType = "varchar(24)")
    private String executeType;
    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", defaultValue = "'0'", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;


}