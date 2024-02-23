package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.platform.application.command.UserRoleCommand;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface UserRoleApplication {


    /**
     * describe 新增用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> save(UserRoleCommand userRoleCommand);

    /**
     * describe 更新用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> update(UserRoleCommand userRoleCommand);

    /**
     * describe 查询单个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> findOne(UserRoleCommand userRoleCommand);

    /**
     * describe 查询多个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<UserRole>> findList(UserRoleCommand userRoleCommand);

    /**
     * describe 删除用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<UserRole> delete(UserRoleCommand userRoleCommand);

}