package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "user_role",  comment = "用户角色关联关系")
@Schema(title = "user_role", description = "用户角色关联关系")
public class UserRoleDO {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 关联ID
     */
    @Schema(description = "关联ID", name = "id")
    @LazyTableFieldId(name = "id", comment = "关联ID")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID", name = "roleId")
    @LazyTableField(name = "role_id", comment = "角色ID", columnType = "bigint")
    private Long roleId;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP", upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", name = "userId")
    @LazyTableField(name = "user_id", comment = "用户ID", columnType = "bigint")
    private Long userId;

}