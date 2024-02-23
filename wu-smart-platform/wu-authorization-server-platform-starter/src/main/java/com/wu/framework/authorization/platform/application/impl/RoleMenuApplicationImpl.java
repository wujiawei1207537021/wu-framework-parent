package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.RoleMenuApplication;
import com.wu.framework.authorization.platform.application.assembler.RoleMenuDTOAssembler;
import com.wu.framework.authorization.platform.application.command.RoleMenuCommand;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenuRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class RoleMenuApplicationImpl implements RoleMenuApplication {

    @Autowired
    RoleMenuRepository roleMenuRepository;

    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> save(RoleMenuCommand roleMenuCommand) {
        RoleMenu roleMenu = RoleMenuDTOAssembler.toRoleMenu(roleMenuCommand);
        return roleMenuRepository.story(roleMenu);
    }

    /**
     * describe 更新角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> update(RoleMenuCommand roleMenuCommand) {
        RoleMenu roleMenu = RoleMenuDTOAssembler.toRoleMenu(roleMenuCommand);
        return roleMenuRepository.story(roleMenu);
    }

    /**
     * describe 查询单个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> findOne(RoleMenuCommand roleMenuCommand) {
        RoleMenu roleMenu = RoleMenuDTOAssembler.toRoleMenu(roleMenuCommand);
        return roleMenuRepository.findOne(roleMenu);
    }

    /**
     * describe 查询多个角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<RoleMenu>> findList(RoleMenuCommand roleMenuCommand) {
        RoleMenu roleMenu = RoleMenuDTOAssembler.toRoleMenu(roleMenuCommand);
        return roleMenuRepository.findList(roleMenu);
    }

    /**
     * describe 删除角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<RoleMenu> delete(RoleMenuCommand roleMenuCommand) {
        RoleMenu roleMenu = RoleMenuDTOAssembler.toRoleMenu(roleMenuCommand);
        return roleMenuRepository.remove(roleMenu);
    }

}