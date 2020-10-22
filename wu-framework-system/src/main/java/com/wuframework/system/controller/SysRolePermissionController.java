package com.wuframework.system.controller;

import com.wuframework.response.Result;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.annotation.RequiredPermission;
import com.wuframework.system.serivce.SysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/***
 * 系统角色权限
 */
@Api(tags = "系统角色权限")
@CustomController("/system/role/permission")
public class SysRolePermissionController {

    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    public SysRolePermissionController(SysRolePermissionService defaultSysRolePermissionService, SysRolePermissionService sysRolePermissionService) {
        if (ObjectUtils.isEmpty(sysRolePermissionService)) {
            this.sysRolePermissionService = defaultSysRolePermissionService;
        } else {
            this.sysRolePermissionService = sysRolePermissionService;
        }
    }

    /**
     * 只有更新 {@link #update(Integer, List)}
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Deprecated
    @ApiOperation("添加角色权限")
    @PostMapping("/{roleId}")
    public Result save(@ApiParam("角色ID") @PathVariable Integer roleId, @NotNull @ApiParam("权限IDs") @RequestBody() List<Integer> permissionIds) {
        return sysRolePermissionService.save(roleId, permissionIds);
    }

    @RequiredRole("super_admin")
    @RequiredPermission
    @ApiOperation("更新角色权限")
    @PutMapping("/{roleId}")
    public Result update(@ApiParam("角色ID") @PathVariable() Integer roleId, @ApiParam("权限IDs") @RequestBody() List<Integer> permissionIds) {
        return sysRolePermissionService.update(roleId, permissionIds);
    }

    @ApiOperation("查找角色权限")
    @GetMapping("/{roleId}")
    public Result query(@ApiParam("角色ID") @PathVariable Integer roleId) {
        return sysRolePermissionService.query(roleId);
    }


}
