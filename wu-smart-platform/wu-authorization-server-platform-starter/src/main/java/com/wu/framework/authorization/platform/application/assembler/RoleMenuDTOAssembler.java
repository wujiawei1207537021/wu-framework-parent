package com.wu.framework.authorization.platform.application.assembler;


import com.wu.framework.authorization.platform.application.command.RoleMenuCommand;
import com.wu.framework.authorization.platform.application.dto.RoleMenuDTO;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class RoleMenuDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static RoleMenu toRoleMenu(RoleMenuCommand roleMenuCommand) {
        if (null != roleMenuCommand) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setCreateTime(roleMenuCommand.getCreateTime());
            roleMenu.setId(roleMenuCommand.getId());
            roleMenu.setIsDeleted(roleMenuCommand.getIsDeleted());
            roleMenu.setMenuId(roleMenuCommand.getMenuId());
            roleMenu.setRoleId(roleMenuCommand.getRoleId());
            roleMenu.setUpdateTime(roleMenuCommand.getUpdateTime());
            return roleMenu;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static RoleMenuDTO fromRoleMenu(RoleMenu roleMenu) {
        if (null != roleMenu) {
            RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
            roleMenuDTO.setCreateTime(roleMenu.getCreateTime());
            roleMenuDTO.setId(roleMenu.getId());
            roleMenuDTO.setIsDeleted(roleMenu.getIsDeleted());
            roleMenuDTO.setMenuId(roleMenu.getMenuId());
            roleMenuDTO.setRoleId(roleMenu.getRoleId());
            roleMenuDTO.setUpdateTime(roleMenu.getUpdateTime());
            return roleMenuDTO;
        }
        return null;
    }

}