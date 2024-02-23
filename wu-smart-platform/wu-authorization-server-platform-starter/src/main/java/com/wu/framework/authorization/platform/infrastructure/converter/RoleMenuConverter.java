package com.wu.framework.authorization.platform.infrastructure.converter;


import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class RoleMenuConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static RoleMenu toRoleMenu(RoleMenuDO roleMenuDO) {
        if (null != roleMenuDO) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setCreateTime(roleMenuDO.getCreateTime());
            roleMenu.setId(roleMenuDO.getId());
            roleMenu.setIsDeleted(roleMenuDO.getIsDeleted());
            roleMenu.setMenuId(roleMenuDO.getMenuId());
            roleMenu.setRoleId(roleMenuDO.getRoleId());
            roleMenu.setUpdateTime(roleMenuDO.getUpdateTime());
            return roleMenu;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static RoleMenuDO fromRoleMenu(RoleMenu roleMenu) {
        if (null != roleMenu) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setCreateTime(roleMenu.getCreateTime());
            roleMenuDO.setId(roleMenu.getId());
            roleMenuDO.setIsDeleted(roleMenu.getIsDeleted());
            roleMenuDO.setMenuId(roleMenu.getMenuId());
            roleMenuDO.setRoleId(roleMenu.getRoleId());
            roleMenuDO.setUpdateTime(roleMenu.getUpdateTime());
            return roleMenuDO;
        }
        return null;
    }

}