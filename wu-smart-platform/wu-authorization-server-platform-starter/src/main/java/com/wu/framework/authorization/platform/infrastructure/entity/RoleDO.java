package com.wu.framework.authorization.platform.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.field.LazyFieldCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "role",  comment = "角色")
@Schema(title = "role", description = "角色")
public class RoleDO {


    /**
     * 角色code
     */
    @Schema(description = "角色code", name = "code")
    @LazyTableField(name = "code", comment = "角色code", columnType = "varchar(255)")
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称", name = "name")
    @LazyTableField(name = "name", comment = "角色名称", columnType = "varchar(255)")
    private String name;

    /**
     * 状态
     */
    @LazyFieldCondition(ignore = true)
    @LazyTableField(name = "status", comment = "状态", columnType = "tinyint(1)")
    private boolean status;
    /**
     * 角色父编码
     */
    @Schema(description = "角色父编码", name = "parentCode")
    @LazyTableField(name = "parent_code", comment = "角色父编码", columnType = "varchar(255)")
    private String parentCode;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = " on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}