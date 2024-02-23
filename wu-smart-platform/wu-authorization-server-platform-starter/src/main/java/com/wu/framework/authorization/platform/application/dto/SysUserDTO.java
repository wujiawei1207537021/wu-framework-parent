package com.wu.framework.authorization.platform.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO
 **/
@Data
@Accessors(chain = true)
@Schema(title = "sys_user_command", description = "")
public class SysUserDTO {


    /**
     * 额外字段
     */
    @Schema(description = "额外字段", name = "columnName")
    private String columnName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", name = "id")
    private Long id;

    /**
     * null
     */
    @Schema(description = "null", name = "isDeleted")
    private Boolean isDeleted;

    /**
     * 密码
     */
    @Schema(description = "密码", name = "password")
    private String password;

    /**
     * 权限
     */
    @Schema(description = "权限", name = "permissionList")
    private List permissionList;

    /**
     * 角色
     */
    @Schema(description = "角色", name = "roleSignList")
    private List roleSignList;

    /**
     *
     */
    @Schema(description = "", name = "scope")
    private String scope;

    /**
     * 状态
     */
    @Schema(description = "状态", name = "status")
    private Boolean status;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

    /**
     * 用户名
     */
    @Schema(description = "用户名", name = "username")
    private String username;

}