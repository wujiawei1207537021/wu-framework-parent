package com.wuframework.system.serivce.def;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.dto.MenuNode;
import com.wuframework.system.common.entity.SysMenu;
import com.wuframework.system.persistence.mapper.SysMenuMapper;
import com.wuframework.system.serivce.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service
public class DefaultSysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 获取菜单树形结构
     *
     * @return
     */
    @Override
    public List<SysMenu> selectMenuList() {
        return this.sysMenuMapper.selectList(null);
    }

    /**
     * 根据菜单编号查找菜单
     *
     * @param menuId 菜单编号
     * @return
     */
    @Override
    public SysMenu getMenuByMenuId(Integer menuId) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(menuId);
        return sysMenuMapper.selectById(menu);
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单
     * @return
     */
    @Override
    public Integer saveMenu(SysMenu menu) {
        return this.sysMenuMapper.insert(menu);
    }

    /**
     * 根据ID更新菜单目录
     *
     * @param menu 菜单
     * @return
     */
    @Override
    public Integer updateMenuByMenuId(SysMenu menu) {
        return this.sysMenuMapper.updateById(menu);
    }

    /**
     * 根据ID更新菜单目录
     *
     * @param menuId 菜单ID
     * @return
     */
    @Override
    public Integer deleteMenuByMenuId(Integer menuId) {
        return this.sysMenuMapper.deleteById(menuId);
    }

    /**
     * 初始化菜单树
     *
     * @param menuNodeList
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Result initMenuTree(List<MenuNode> menuNodeList) {
        baseMapper.delete(null);
        for (MenuNode menuNode : menuNodeList) {
            SysMenu sysMenu=new SysMenu();
            sysMenu.setName(menuNode.getName());
            sysMenu.setIcon(menuNode.getIcon());
            sysMenu.setUrl(menuNode.getPath());
            sysMenu.setType(0);
            sysMenu.setParentId(0);
            sysMenu.insert();
            for (MenuNode child : menuNode.getChildren()) {
                SysMenu temp=new SysMenu();
                temp.setName(child.getName());
                temp.setIcon(child.getIcon());
                temp.setUrl(child.getPath());
                temp.setType(1);
                temp.setParentId(sysMenu.getMenuId());
                temp.insert();
            }
        }
        return ResultFactory.successOf();
    }

    /**
     * 根据用户ID查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public List<SysMenu> getMenuListByUserId(Integer userId) {
        return this.sysMenuMapper.selectMenuListByUserId(userId);
    }

    /**
     * 根据当前用户查询相关菜单
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public List<SysMenu> getMenuListByUser(Integer userId) {
        return this.sysMenuMapper.selectMenuListByUser(userId);
    }

    /**
     * 根据登录的用户获取二级菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getSecondaryMenu(Integer userId) {
        return this.sysMenuMapper.selectSecondaryMenuListByUserId(userId);
    }

    /**
     * 根据角色编号获取菜单列表
     *
     * @param roleId 角色编号
     * @return
     */
    @Override
    public List<SysMenu> getMenuListByRoleId(Long roleId) {
        return this.sysMenuMapper.selectMenuListByRoleId(roleId);
    }

}
