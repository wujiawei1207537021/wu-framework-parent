package com.wu.smart.acw.server.infrastructure.entity;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 数据库服务器
 *
 * @author Jia wei Wu
 * @date 2023/10/24 03:15 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_instance",  comment = "数据库服务器")
@Schema(title = "acw_instance", description = "数据库服务器")
public class AcwInstanceDO {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 链接驱动
     */
    @Schema(description = "链接驱动", name = "driverClassName")
    @LazyTableField(name = "driver_class_name", comment = "链接驱动", columnType = "varchar(255)")
    private String driverClassName;

    /**
     * getHost
     */
    @Schema(description = "getHost", name = "getHost")
    @LazyTableField(name = "getHost", comment = "getHost", columnType = "varchar(255)")
    private String host;

    /**
     *
     */
    @Schema(description = "", name = "id")
    @LazyTableField(name = "id", comment = "", lazyTableIndexs = {@LazyTableIndex(indexName = "i", indexType = LayerField.LayerFieldType.UNIQUE)}, columnType = "varchar(255)")
    private String id;

    /**
     * 初始化数据库到本地
     */
    @Schema(description = "初始化数据库到本地", name = "initializeToLocal")
    @LazyTableField(name = "initialize_to_local", comment = "初始化数据库到本地", columnType = "tinyint(1)")
    private Boolean initializeToLocal;

    /**
     *
     */
    @Schema(description = "", name = "instanceName")
    @LazyTableField(name = "instance_name", comment = "", columnType = "varchar(255)")
    private String instanceName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 密码
     */
    @Schema(description = "密码", name = "password")
    @LazyTableField(name = "password", comment = "密码", columnType = "varchar(255)")
    private String password;

    /**
     * 端口
     */
    @Schema(description = "端口", name = "getPort")
    @LazyTableField(name = "getPort", comment = "端口", columnType = "int")
    private Integer port;

    /**
     * 排序
     */
    @Schema(description = "排序", name = "sort")
    @LazyTableField(name = "sort", comment = "排序", defaultValue = "'0'", columnType = "int")
    private Integer sort;

    /**
     * 状态
     */
    @Schema(description = "状态", name = "status")
    @LazyTableField(name = "status", comment = "状态", columnType = "int")
    private Integer status;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 链接地址
     */
    @Schema(description = "链接地址", name = "url")
    @LazyTableField(name = "url", comment = "链接地址", columnType = "varchar(255)")
    private String url;

    /**
     * 用户名
     */
    @Schema(description = "用户名", name = "username")
    @LazyTableField(name = "username", comment = "用户名", columnType = "varchar(255)")
    private String username;

}