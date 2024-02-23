package com.wuframework.system.serivce;


import com.baomidou.mybatisplus.service.IService;
import com.wuframework.response.Result;
import com.wuframework.system.common.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 添加或修改角色的菜单权限
     *
     * @param roleId 角色ID
     * @param menuId 菜单列表
     * @return
     */
    Integer saveRoleMenu(Long roleId, List<String> menuId);

    /**
     * 根据角色ID和菜单ID绑定默认菜单
     *
     * @param roleId
     * @param menuId
     * @return
     */
    Result saveRoleDefaultPage(Long roleId, Long menuId);

    /**
     * 获取指定角色下的菜单
     *
     * @param roleId
     * @return
     */
    List<SysRoleMenu> listByRoleId(Long roleId);

}
