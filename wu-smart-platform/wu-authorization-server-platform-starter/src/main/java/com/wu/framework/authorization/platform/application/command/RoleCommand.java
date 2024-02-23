package com.wu.framework.authorization.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "role_command", description = "角色")
public class RoleCommand {


    /**
     * 角色code
     */
    @Schema(description = "角色code", name = "code")
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称", name = "name")
    private String name;
    /**
     * 状态
     */
    @Schema(description = "状态", name = "status")
    private boolean status;

    /**
     * 角色父编码
     */
    @Schema(description = "角色父编码", name = "parentCode")
    private String parentCode;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 菜单ID
     */
    private List<Long> menuIds;

}