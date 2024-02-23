package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.UserRoleApplication;
import com.wu.framework.authorization.platform.application.assembler.UserRoleDTOAssembler;
import com.wu.framework.authorization.platform.application.command.UserRoleCommand;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.authorization.platform.model.user.role.UserRoleRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class UserRoleApplicationImpl implements UserRoleApplication {

    @Autowired
    UserRoleRepository userRoleRepository;

    /**
     * describe 新增用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> save(UserRoleCommand userRoleCommand) {
        UserRole userRole = UserRoleDTOAssembler.toUserRole(userRoleCommand);
        return userRoleRepository.story(userRole);
    }

    /**
     * describe 更新用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> update(UserRoleCommand userRoleCommand) {
        UserRole userRole = UserRoleDTOAssembler.toUserRole(userRoleCommand);
        return userRoleRepository.story(userRole);
    }

    /**
     * describe 查询单个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> findOne(UserRoleCommand userRoleCommand) {
        UserRole userRole = UserRoleDTOAssembler.toUserRole(userRoleCommand);
        return userRoleRepository.findOne(userRole);
    }

    /**
     * describe 查询多个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<UserRole>> findList(UserRoleCommand userRoleCommand) {
        UserRole userRole = UserRoleDTOAssembler.toUserRole(userRoleCommand);
        return userRoleRepository.findList(userRole);
    }

    /**
     * describe 删除用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<UserRole> delete(UserRoleCommand userRoleCommand) {
        UserRole userRole = UserRoleDTOAssembler.toUserRole(userRoleCommand);
        return userRoleRepository.delete(userRole);
    }

}