package com.wuframework.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.db.annotation.RequestPage;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.consts.CacheConsts;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysRole;
import com.wuframework.system.redis.RedisRepository;
import com.wuframework.system.serivce.SysRoleService;
import com.wuframework.system.serivce.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Api(tags = "系统角色管理模块")
@CustomController({"/sysrole", "/system/role"})
public class SysRoleController {

    @Autowired
    public SysRoleService sysRoleService;

    @Autowired
    public SysUserRoleService sysUserRoleService;

    @Autowired
    private RedisRepository redisRepository;

    @ApiOperation(value = "分页获取角色数据", notes = "获取当前人员可管理角色清单")
    @GetMapping()
    public Result getRoleListPage(@ModelAttribute SysRole role, @AccessTokenUser DefaultSysUser sysUser, @RequestPage Page page) {
        role.setUserIdCreate(sysUser.getUserId());
        return ResultFactory.successOf(this.sysRoleService.getRoleListPage(role, page));
    }

    @ApiOperation(value = "根据条件获取角色数据列表")
    @GetMapping("/all")
    public Result getRoleList(@ModelAttribute SysRole role) {
        EntityWrapper<SysRole> entityWrapper = createWrapper(role);
        return ResultFactory.successOf(this.sysRoleService.selectList(entityWrapper));
    }

    @ApiOperation(value = "根据角色编号获取角色")
    @GetMapping("/{roleId}")
    public Result getRoleByRoleId(@PathVariable Integer roleId) {
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        return ResultFactory.successOf(this.sysRoleService.selectById(role));
    }

    @ApiOperation(value = "根据用户ID获取相关角色")
    @GetMapping("/user/{userId}")
    public Result getRoleByUserId(@PathVariable Long userId) {
        return ResultFactory.successOf(this.sysRoleService.selectRoleByUserId(userId));
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "新增角色", notes = "提交角色数据")
    @PostMapping()
    public Result createRole(@RequestBody SysRole role, @AccessTokenUser DefaultSysUser sysUser) {
        role.setUserIdCreate(sysUser.getUserId());
        role.setGmtCreate(new Date());
        if (this.sysRoleService.insert(role)) {
            return ResultFactory.successOf("success");
        }
        return ResultFactory.errorOf("新增出错!");
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "更新角色信息")
    @PutMapping()
    public Result updateRole(@RequestBody final SysRole role) {
        role.setGmtModified(new Date());
        if (this.sysRoleService.update(role, new EntityWrapper<SysRole>().eq("role_id", role.getRoleId()))) {
            // 2. 注销拥有该角色的用户的 redis 数据
            this.sysUserRoleService.listByRoleId(role.getRoleId()).stream()
                    .map(userRole -> userRole.getUserId().toString())
                    .forEach(userId -> this.redisRepository.del(CacheConsts.REDIS_USER + userId));
            return ResultFactory.successOf("success");
        }
        return ResultFactory.errorOf("更新出错!");
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @Deprecated
    @ApiOperation(value = "删除角色-物理删除", notes = "物理删除角色数据")
    @DeleteMapping("/{roleId}")
    public Result deleteRoleByRoleId(@PathVariable("roleId") final Long roleId) {
        Integer count = sysUserRoleService.countByRoleId(roleId);
        if (count > 0) {
            return ResultFactory.errorOf("请先解除该角色关联的用户,在进行删除");
        }
        return ResultFactory.successOf(this.sysRoleService.deleteRoleByRoleId(roleId));
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "根据角色ID逻辑删除角色", notes = "逻辑删除-（数组）")
    @PutMapping("/batchdelete")
    public Result batchDeleteRoleByRoleId(@RequestBody final Map<String, Integer[]> roleId) {
        final Integer[] roleIds = roleId.get("roleId");
        final int row = this.sysRoleService.batchDeleteRoleByRoleId(roleIds);
        if (roleIds.length == row) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    @ApiOperation(value = "获取当前用户下所管理的角色")
    @GetMapping("/manage-roles")
    public Result getManagementRoleList(@AccessTokenUser DefaultSysUser user) {
        List<SysRole> sysRoleList = user.getRoles();
        Set<String> roleIds = new HashSet<>();
        if (!ObjectUtils.isEmpty(sysRoleList)) {
            for (SysRole role : sysRoleList) {
                if (StringUtils.isNotBlank(role.getManageableRole())) {
                    roleIds.addAll(new HashSet<>(Arrays.asList(StringUtils.split(role.getManageableRole(), ","))));
                }
            }
        }
        List<SysRole> sysRoles = new ArrayList<>();
        if (roleIds.isEmpty()) {
            return ResultFactory.successOf(sysRoles);
        } else {
            EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>();
            entityWrapper.in("role_id", roleIds);
            sysRoles = sysRoleService.selectList(entityWrapper);
            return ResultFactory.successOf(sysRoles);
        }
    }

    /**
     * 根据请求的实体创建检索条件
     *
     * @param role
     * @return
     */
    private EntityWrapper<SysRole> createWrapper(final SysRole role) {
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.setEntity(new SysRole());
        Optional<SysRole> optional = Optional.ofNullable(role);
        optional.map(SysRole::getRoleName).ifPresent(dishName -> entityWrapper.eq("role_name", role.getRoleName()));
        optional.map(SysRole::getRoleSign).ifPresent(deptId -> entityWrapper.eq("role_sign", role.getRoleSign()));
        optional.map(SysRole::getStatus).ifPresent(dishId -> entityWrapper.ne("status", role.getStatus()));
        return entityWrapper;
    }
}
