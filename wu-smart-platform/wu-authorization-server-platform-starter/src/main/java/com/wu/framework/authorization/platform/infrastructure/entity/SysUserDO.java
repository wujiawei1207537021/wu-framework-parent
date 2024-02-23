package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "sys_user",  comment = "用户")
@Schema(title = "sys_user", description = "")
public class SysUserDO {



    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", name = "id")
    @LazyTableFieldId(name = "id", comment = "用户ID")
    private Long id;

    /**
     * null
     */
    @Schema(description = "null", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "null", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 密码
     */
    @Schema(description = "密码", name = "password")
    @LazyTableField(name = "password", comment = "密码", columnType = "varchar(255)")
    private String password;


    /**
     *
     */
    @Schema(description = "", name = "scope")
    private String scope;

    /**
     * 状态
     */
    @Schema(description = "状态", name = "status")
    @LazyTableField(name = "status", comment = "状态", columnType = "tinyint(1)")
    private Boolean status;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 用户名
     */
    @Schema(description = "用户名", name = "username")
    @LazyTableFieldUnique(name = "username", comment = "用户名", columnType = "varchar(255)")
    private String username;

}