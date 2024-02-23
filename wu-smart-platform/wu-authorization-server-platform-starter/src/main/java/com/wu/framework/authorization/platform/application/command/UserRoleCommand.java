package com.wu.framework.authorization.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "user_role_command", description = "用户角色关联关系")
public class UserRoleCommand {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 关联ID
     */
    @Schema(description = "关联ID", name = "id")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID", name = "roleId")
    private Long roleId;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", name = "userId")
    private Long userId;

}