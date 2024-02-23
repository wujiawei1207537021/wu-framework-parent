package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "interface",  comment = "接口")
@Schema(title = "interface", description = "接口")
public class InterfaceDO {


    /**
     * 应用名称
     */
    @Schema(description = "应用名称", name = "applicationName")
    @LazyTableField(name = "application_name", comment = "应用名称", columnType = "varchar(20)")
    private String applicationName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 接口描述
     */
    @Schema(description = "接口描述", name = "description")
    @LazyTableField(name = "description", comment = "接口描述", columnType = "varchar(255)")
    private String description;

    /**
     * 父路径
     */
    @Schema(description = "父路径", name = "parentPath")
    @LazyTableField(name = "parent_path", comment = "父路径", columnType = "json")
    private List<String> parentPath;

    /**
     * 路径
     */
    @Schema(description = "路径", name = "path")
    @LazyTableField(name = "path", comment = "路径", columnType = "varchar(100)")
    private List<String> path;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法", name = "requestMethods")
    @LazyTableField(name = "request_methods", comment = "请求方法", columnType = "varchar(255)")
    private List<RequestMethod> requestMethods;

    /**
     * 接口标签
     */
    @Schema(description = "接口标签", name = "tag")
    @LazyTableField(name = "tag", comment = "接口标签", columnType = "varchar(100)")
    private List<String> tag;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}