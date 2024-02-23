package com.wuframework.system.controller;


import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.system.serivce.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Api(tags = "系统角色管理模块")
@CustomController({"/sysrolemenu", "/system/role/menu"})
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    //TODO
    @ApiOperation(value = "编辑角色授权菜单", notes = "添加或修改角色的菜单权限")
    @PostMapping("/{roleId}")
    public Result saveRoleMenu(@ApiParam(name = "roleId", value = "角色ID", required = true) @PathVariable(value = "roleId") Long roleId,
                               @ApiParam(name = "menuId", value = "菜单列表-（数组）") @RequestBody List<String> menuId) {
        int row = this.sysRoleMenuService.saveRoleMenu(roleId, menuId);
        boolean existed = row == menuId.size() || (!ObjectUtils.isEmpty(menuId) && row == 0);
        if (existed) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
    }

    @ApiOperation(value = "根据角色ID和菜单ID绑定默认菜单", notes = "用户登录展示默认菜单页")
    @RequestMapping("/saveRoleDefaultPage")
    public Result saveRoleDefaultPage(@Param(value = "roleId") Long roleId, @Param(value = "menuId") Long menuId) {
        return ResultFactory.successOf(sysRoleMenuService.saveRoleDefaultPage(roleId, menuId));
    }

}
