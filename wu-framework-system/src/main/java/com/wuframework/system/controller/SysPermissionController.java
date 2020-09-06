package com.wuframework.system.controller;

import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.mark.ValidType;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.entity.SysPermission;
import com.wuframework.system.common.enums.PermissionTypeEnums;
import com.wuframework.system.serivce.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限
 */
@Api(tags = "系统权限")
@CustomController("/system/permission")
public class SysPermissionController {


    private SysPermissionService sysPermissionService;

    @Autowired
    public SysPermissionController(SysPermissionService defaultSysPermissionService, SysPermissionService sysPermissionService) {
        if (ObjectUtils.isEmpty(sysPermissionService)) {
            this.sysPermissionService = defaultSysPermissionService;
        } else {
            this.sysPermissionService = sysPermissionService;
        }
    }

    @RequiredRole("super_admin")
    @ApiOperation("添加权限")
    @PostMapping()
    public Result save(@Validated(ValidType.Create.class) @RequestBody SysPermission sysPermission) {
        return sysPermissionService.save(sysPermission);
    }
    @RequiredRole("super_admin")
    @ApiOperation("更新权限")
    @PutMapping()
    public Result update(@Validated(ValidType.Update.class) @RequestBody SysPermission sysPermission) {
        return sysPermissionService.update(sysPermission);
    }
    @RequiredRole("super_admin")
    @ApiOperation("超级管理员查询所有权限")
    @GetMapping()
    public Result queryList(@ModelAttribute UniversalSearchQO universalSearchQO) {
        return sysPermissionService.queryList(universalSearchQO);
    }
//    @RequiredRole("super_admin")
    @ApiOperation("查询权限类型")
    @GetMapping("/type")
    public Result queryType() {
       return ResultFactory.successOf(PermissionTypeEnums.values());
    }
}
