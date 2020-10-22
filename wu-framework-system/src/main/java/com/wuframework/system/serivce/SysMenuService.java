package com.wuframework.system.serivce;


import com.baomidou.mybatisplus.service.IService;
import com.wuframework.response.Result;
import com.wuframework.system.common.dto.MenuNode;
import com.wuframework.system.common.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据用户ID查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    List<SysMenu> getMenuListByUserId(Integer userId);

    /**
     * 根据用户查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    List<SysMenu> getMenuListByUser(Integer userId);

    /**
     * 根据用户查询相关二级菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> getSecondaryMenu(Integer userId);

    /**
     * 根据角色编号获取菜单列表
     *
     * @param roleId 角色编号
     * @return
     */
    List<SysMenu> getMenuListByRoleId(Long roleId);

    /**
     * 获取菜单树形结构
     *
     * @return
     */
    List<SysMenu> selectMenuList();

    /**
     * 根据菜单ID获取相关菜单
     *
     * @param menuId 菜单编号
     * @return
     */
    SysMenu getMenuByMenuId(Integer menuId);

    /**
     * 添加菜单
     *
     * @param menu 菜单
     * @return
     */
    Integer saveMenu(SysMenu menu);

    /**
     * 根据ID更新菜单目录
     *
     * @param menu 菜单
     * @return
     */
    Integer updateMenuByMenuId(SysMenu menu);

    /**
     * 根据ID更新菜单目录
     *
     * @param menuId 菜单ID
     * @return
     */
    Integer deleteMenuByMenuId(Integer menuId);

    /**
     * 初始化菜单树
     *
     * @param menuNodeList
     * @return
     */
    Result initMenuTree(List<MenuNode> menuNodeList);
}
