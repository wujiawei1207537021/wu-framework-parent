package com.wuframework.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.db.annotation.RequestPage;
import com.wuframework.file.wrapper.WuFastDFSClientWrapper;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.dto.UserDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.pro.ApplicationProperties;
import com.wuframework.system.common.vo.SimpleSelectVo;
import com.wuframework.system.serivce.SysUserService;
import com.wuframework.system.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Slf4j
@Api(tags = "系统用户管理模块")
@CustomController({"/sysuser", "/system/user"})
public class SysUserController {

    private SysUserService sysUserService;


    @Resource
    private WuFastDFSClientWrapper fastDFSClientWrapper;

    @Resource
    private ApplicationProperties applicationProperties;


    @Autowired
    public SysUserController(SysUserService defaultSysUserService, SysUserService sysUserService) {
        if (ObjectUtils.isEmpty(sysUserService)) {
            this.sysUserService = defaultSysUserService;
        } else {
            this.sysUserService = sysUserService;
        }
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    public Result info(@AccessTokenUser DefaultSysUserDetails defaultSysUserDetails) {
        return sysUserService.info(defaultSysUserDetails);
    }

    /**
     * 获取人员列表
     *
     * @return
     */
    @RequiredRole(orRoles = {"super_admin", "manufacturer"})
    @GetMapping()
    @ApiOperation(value = "获取人员列表")
    public Result get(@RequestPage final Page page,
                      @ModelAttribute("user") final DefaultSysUser defaultSysUserQO,
                      @RequestParam(defaultValue = "false", required = false) boolean pagination,
                      @AccessTokenUser DefaultSysUserDetails defaultSysUserDetails) {
        return sysUserService.getAllByPage(page, defaultSysUserQO, pagination, defaultSysUserDetails);
    }

    @Deprecated
    @GetMapping("/all")
    @ApiOperation(value = "获取人员列表包含部门")
    public Result getUserAllByUserDTO(@ModelAttribute("userDTO") final UserDTO userDTO,
                                      @RequestPage final Page page,
                                      @AccessTokenUser final DefaultSysUserDetails sysUser) {
        return sysUserService.getUserAllByUserDTO(userDTO, sysUser, page);
    }

    /**
     * 新增人员
     *
     * @param sysUser
     * @param defaultSysUserDetails
     * @return
     */
    @RequiredRole(orRoles = {"manufacturer", "super_admin"})
    @PostMapping()
    @ApiOperation(value = "人员新增")
    public Result save(@RequestBody DefaultSysUser sysUser, @AccessTokenUser DefaultSysUserDetails defaultSysUserDetails) {
        return sysUserService.save(sysUser, defaultSysUserDetails);
    }

    /**
     * 修改人员
     *
     * @param defaultSysUser
     * @return
     */
    @RequiredRole(orRoles = {"manufacturer", "super_admin"})
    @PutMapping()
    @ApiOperation(value = "修改人员")
    public Result updateUser(@RequestBody final DefaultSysUser defaultSysUser) {
        sysUserService.updateUser(defaultSysUser);
        return ResultFactory.successOf(defaultSysUser.getUserId().toString());
    }

    /**
     * 停用人员
     *
     * @return
     */
    @RequiredRole(orRoles = {"manufacturer", "super_admin"})
    @PostMapping("/stop")
    @ApiOperation(value = "停用人员")
    public Result stop(final Long userId) {
        final DefaultSysUser defaultSysUser = new DefaultSysUser();
        defaultSysUser.setStatus(0);
//        defaultSysUser.setGmtModified(LocalDateTime.now());
        return ResultFactory.successOf(this.sysUserService.update(defaultSysUser, new EntityWrapper<DefaultSysUser>().eq("user_id", userId)));
    }

    /**
     * 获取人员的vo对象
     * 包含部门未启用
     *
     * @return
     */
    @Deprecated
    @ApiOperation(value = "获取人员的Select对象")
    @GetMapping("/select")
    public Result getSimpleSelect(@ModelAttribute("userDTO") UserDTO userDTO, @AccessTokenUser DefaultSysUserDetails sysUser) {
        String deptIds = RedisUtils.redisUtils.getChildDeptListStrFromRedisByParentId(sysUser.getDeptId());
        userDTO.setDeptIds(deptIds);
        final List<SimpleSelectVo> simpleSelectVos = this.sysUserService.getSimpleSelectVoList(userDTO);
        for (final SimpleSelectVo vo : simpleSelectVos) {
            vo.setKey(vo.getRowKey());
        }
        return ResultFactory.successOf(simpleSelectVos);
    }

    /**
     * 修改密码
     *
     * @param user
     * @param data
     * @return
     */
    @PostMapping("/restPwd")
    @ApiOperation(value = "修改密码")
    public Result resetPassword(@AccessTokenUser final DefaultSysUserDetails defaultSysUserDetails, @RequestBody final Map<String, String> data) {
        final String oldPasswords = DigestUtils.md5Hex(data.get("password"));
        final String newPasswords = DigestUtils.md5Hex(data.get("newPassword"));
        final DefaultSysUser userPassword = this.sysUserService.selectById(defaultSysUserDetails.getUserId());
        if (StringUtils.equalsIgnoreCase(userPassword.getPassword(), oldPasswords)) {
            userPassword.setPassword(newPasswords);
//            userPassword.setGmtModified(LocalDateTime.now());
            return ResultFactory.successOf(this.sysUserService.updateById(userPassword));
        }
        return ResultFactory.errorOf("原密码不正确");
    }

    /**
     * 重置密码
     *
     * @param data
     * @return
     */
    @RequiredRole
    @PostMapping("/restuserpwd")
    @ApiOperation(value = "重置密码")
    public Result resetUserPassword(@RequestBody Map<String, String> data) {
        String password = data.get("password");
        Integer userId = Integer.valueOf(data.get("userId"));
        if (StringUtils.isBlank(password) || null == userId) {
            return ResultFactory.errorOf("用户和重置密码不能为空");
        }
        try {
            DefaultSysUser users = new DefaultSysUser();
            users.setUserId(userId);
            users.setPassword(DigestUtils.md5Hex(password));
//            users.setGmtCreate(LocalDateTime.now());
            boolean success = this.sysUserService.updateById(users);
            if (success) {
                return ResultFactory.successOf(success);
            }
            return ResultFactory.of(DefaultResultCode.DEFAULT_ERROR);
        } catch (Exception e) {
            return ResultFactory.errorOf("原密码不正确");
        }
    }

    /**
     * 根据角色查询用户列表
     *
     * @param roleId 角色编号
     * @return
     */
    @RequiredRole(orRoles = {"manufacturer", "super_admin"})
    @ApiOperation(value = "根据角色编号查询用户列表")
    @GetMapping("/role/{roleId}")
    public Result getUserListByRole(@PathVariable(value = "roleId") final Long roleId) {
        return ResultFactory.successOf(this.sysUserService.getUserListByRoleId(roleId));
    }

    /**
     * 根据部门编号获取人员
     *
     * @param deptId 部门编号
     * @param all    查询所有子节点
     * @return
     */
    @GetMapping("/dept/{deptId}/{all}")
    public Result getUserListByDept(@PathVariable(value = "deptId") final String deptId,
                                    @PathVariable(value = "all") final String all) {
        return ResultFactory.successOf(this.sysUserService.getUserListByDept("1".equals(all), deptId));
    }

    /**
     * 获取采样人员
     *
     * @return
     */
    @GetMapping("/person")
    public Result person(@AccessTokenUser final DefaultSysUser user) {
        final List<DefaultSysUser> userList = this.sysUserService.getUserListByDept(true, user.getDeptId().toString());
        return ResultFactory.successOf(userList);
    }

    @ApiOperation(value = "验证手机号是否存在", notes = "true为存在，false不存在")
    @GetMapping("/{mobile}")
    public Result getVerifyMobileNumber(@PathVariable("mobile") final String mobile) {
        return ResultFactory.successOf(this.sysUserService.selectVerifyMobileNumber(null, mobile));
    }

    @ApiOperation(value = "获取所有用户id")
    @GetMapping("/userId")
    public Result getUserId() {
        return ResultFactory.successOf(this.sysUserService.getUserId());
    }

}