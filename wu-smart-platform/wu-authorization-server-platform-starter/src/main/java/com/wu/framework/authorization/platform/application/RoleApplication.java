package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.platform.application.command.RoleCommand;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface RoleApplication {


    /**
     * describe 新增角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> save(RoleCommand roleCommand);

    /**
     * describe 更新角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> update(RoleCommand roleCommand);

    /**
     * describe 查询单个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> findOne(RoleCommand roleCommand);

    /**
     * describe 查询多个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<Role>> findList(RoleCommand roleCommand);

    /**
     * describe 删除角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<Role> delete(RoleCommand roleCommand);

}