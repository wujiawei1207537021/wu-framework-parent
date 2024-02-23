package com.wu.framework.authorization.platform.application;

import com.wu.framework.authorization.platform.application.command.RoleMenuCommand;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface RoleMenuApplication {


    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> save(RoleMenuCommand roleMenuCommand);

    /**
     * describe 更新角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> update(RoleMenuCommand roleMenuCommand);

    /**
     * describe 查询单个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> findOne(RoleMenuCommand roleMenuCommand);

    /**
     * describe 查询多个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<List<RoleMenu>> findList(RoleMenuCommand roleMenuCommand);

    /**
     * describe 删除角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    Result<RoleMenu> delete(RoleMenuCommand roleMenuCommand);

}