package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.platform.application.SysUserApplication;
import com.wu.framework.authorization.platform.application.assembler.SysUserDTOAssembler;
import com.wu.framework.authorization.platform.application.command.SysUserCommand;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenuRepository;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.authorization.platform.model.sys.user.SysUserRepository;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.authorization.platform.model.user.role.UserRoleRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class SysUserApplicationImpl implements SysUserApplication {

    @Autowired
    SysUserRepository sysUserRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> save(SysUserCommand sysUserCommand) {
        SysUser sysUser = SysUserDTOAssembler.toSysUser(sysUserCommand);
        // 保存用户
        Result<SysUser> story = sysUserRepository.story(sysUser);
        // 存储用户角色
        if(story.isSuccess()){
            SysUser data = story.getData();
            Long userId = data.getId();
            List<UserRole> userRoleList = sysUserCommand.getRoleIds().stream().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setIsDeleted(false);
                return userRole;
            }).collect(Collectors.toList());
            userRoleRepository.batchStoryUserRole(userId,userRoleList);
        }
        return story;
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> update(SysUserCommand sysUserCommand) {
        return save(sysUserCommand);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> findOne(SysUserCommand sysUserCommand) {
        SysUser sysUser = SysUserDTOAssembler.toSysUser(sysUserCommand);
        return sysUserRepository.findOne(sysUser);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<SysUser>> findList(SysUserCommand sysUserCommand) {
        SysUser sysUser = SysUserDTOAssembler.toSysUser(sysUserCommand);
        Result<List<SysUser>> sysUserRepositoryList = sysUserRepository.findList(sysUser);

        // 获取用户角色
        return sysUserRepositoryList;
    }
    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/21 11:16 晚上
     **/

    @Override
    public Result<LazyPage<SysUser>> findPage(int size, int current, SysUserCommand sysUserCommand) {
        SysUser sysUser = SysUserDTOAssembler.toSysUser(sysUserCommand);
        return sysUserRepository.findPage(size,current,sysUser);
    }
    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<SysUser> delete(SysUserCommand sysUserCommand) {
        SysUser sysUser = SysUserDTOAssembler.toSysUser(sysUserCommand);
        return sysUserRepository.delete(sysUser);
    }

    /**
     * 查询用户菜单
     *
     * @param userDetails
     * @return
     */
    @Override
    public Result<List<Menu>> findUserMenuList(UserDetails userDetails) {
        Long userId = userDetails.getId();
        // 查询用户角色
        UserRole userRole=new UserRole();
        userRole.setUserId(userId);
        Result<List<UserRole>> userRoleRepositoryList = userRoleRepository.findList(userRole);
        if(userRoleRepositoryList.isSuccess()){
            // 查询角色菜单
            List<Long> roleIds = userRoleRepositoryList.getData().stream().map(UserRole::getRoleId).toList();
            if (!ObjectUtils.isEmpty(roleIds)) {
                return roleMenuRepository.findRoleMenuList(roleIds);
            } else {
                return ResultFactory.successOf(new ArrayList<>());
            }
        }else {
            return ResultFactory.errorOf("无法查询到用户角色"+userRoleRepositoryList.getMessage());
        }

    }
}