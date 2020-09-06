package com.wuframework.system.controller;


import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.system.serivce.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Api(tags = "系统用户管理模块")
@CustomController({"/sysuserrole","/system/user/role"})
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 根据用户存储其拥有的角色
     *
     * @param userId 用户编号
     * @param roleId 角色列表
     * @return
     */
    @ApiOperation(value = "根据用户存储其拥有的角色")
    @PostMapping("user/{userId}")
    public Result saveByUserId(@ApiParam(name = "userId", value = "用户ID", required = true) @PathVariable("userId") Integer userId,
                               @ApiParam(name = "roleId", value = "角色ID-（数组）") @RequestBody List<String> roleId) {
        int row = this.sysUserRoleService.saveByUserId(userId, roleId);
        boolean existed = row == roleId.size() || (!ObjectUtils.isEmpty(roleId) && row == 0);
        if (existed) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
    }

    /**
     * 根据角色存储角色所有用的用户
     *
     * @param roleId 角色编号
     * @param userId 用户列表
     * @return
     */
    @ApiOperation(value = "根据角色存储角色所有用的用户")
    @PostMapping("/role/{roleId}")
    public Result saveByRoleId(@ApiParam(name = "roleId", value = "角色ID", required = true) @PathVariable Integer roleId,
                               @ApiParam(name = "userId", value = "用户ID-（数组）") @RequestBody List<String> userId) {
        int row = this.sysUserRoleService.saveByRoleId(roleId, userId);
        boolean existed = row == userId.size() || (!ObjectUtils.isEmpty(userId) && row == 0);
        if (existed) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
    }
}
