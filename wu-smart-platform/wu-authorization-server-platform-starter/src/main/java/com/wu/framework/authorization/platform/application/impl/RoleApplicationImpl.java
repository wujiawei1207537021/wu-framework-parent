package com.wu.framework.authorization.platform.application.impl;

import com.wu.framework.authorization.platform.application.RoleApplication;
import com.wu.framework.authorization.platform.application.assembler.RoleDTOAssembler;
import com.wu.framework.authorization.platform.application.command.RoleCommand;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.authorization.platform.model.role.RoleRepository;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenuRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class RoleApplicationImpl implements RoleApplication {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMenuRepository roleMenuRepository;

    /**
     * describe 新增角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> save(RoleCommand roleCommand) {
        Role role = RoleDTOAssembler.toRole(roleCommand);
        role.setIsDeleted(false);
        Result<Role> roleResult = roleRepository.story(role);

        Role data = roleResult.getData();
        Long roleId = data.getId();
        List<RoleMenu> roleMenuList = roleCommand.getMenuIds().stream().map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setIsDeleted(false);
            roleMenu.setRoleId(roleId);
            return roleMenu;
        }).collect(Collectors.toList());

        roleMenuRepository.removeRoleMenus(roleCommand.getId());
        roleMenuRepository.batchStory(roleMenuList);
        return roleResult;
    }

    /**
     * describe 更新角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> update(RoleCommand roleCommand) {
        return save(roleCommand);
    }

    /**
     * describe 查询单个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> findOne(RoleCommand roleCommand) {
        Role role = RoleDTOAssembler.toRole(roleCommand);
        return roleRepository.findOne(role);
    }

    /**
     * describe 查询多个角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<List<Role>> findList(RoleCommand roleCommand) {
        Role role = RoleDTOAssembler.toRole(roleCommand);
        return roleRepository.findList(role);
    }

    /**
     * describe 删除角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @Override
    public Result<Role> delete(RoleCommand roleCommand) {
        Role role = RoleDTOAssembler.toRole(roleCommand);
        return roleRepository.delete(role);
    }

}