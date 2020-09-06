package com.wuframework.system.controller;

import com.google.common.collect.Lists;
import com.wuframework.system.common.vo.TreeNode;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.dto.MenuNode;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysMenu;
import com.wuframework.system.serivce.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Api(tags = "系统菜单管理模块")
@CustomController({"/sysmenu","/system/menu"})
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表转换树
     *
     * @param list    菜单树
     * @param startId 起始编号
     * @return
     */
    private List<TreeNode> listToTree(List<SysMenu> list, String startId) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        List<SysMenu> menuList = list.stream()
                .filter(d -> startId.equals(d.getParentId().toString()))
                .collect(Collectors.toList());
        for (SysMenu menu : menuList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setIcon(menu.getIcon());
            treeNode.setKey(menu.getMenuId().toString());
            treeNode.setTitle(menu.getName());
            treeNode.setData(menu);
            List<TreeNode> treeNodes = listToTree(list, menu.getMenuId().toString());
            treeNode.setChildren(treeNodes);
            treeNode.setLeaf(treeNodes.size() == 0);
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    private List<MenuNode> listToTrees(List<SysMenu> list, String startId) {
        List<MenuNode> menuNodeList = new ArrayList<>();
        List<SysMenu> menuList = list.stream()
                .filter(d -> startId.equals(d.getParentId().toString()))
                .collect(Collectors.toList());
        for (SysMenu menu : menuList) {
            MenuNode menuNode = new MenuNode();
            menuNode.setName(menu.getName());
            menuNode.setIcon(menu.getIcon());
            menuNode.setPath(menu.getUrl());
            List<MenuNode> menuNodes = listToTrees(list, menu.getMenuId().toString());
            menuNode.setChildren(menuNodes);
            menuNodeList.add(menuNode);
        }
        return menuNodeList;
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "添加菜单目录")
    @PostMapping()
    public Result saveMenu(@RequestBody SysMenu menu, @AccessTokenUser DefaultSysUser user) {
        menu.setGmtCreate(new Date());
        int row = this.sysMenuService.saveMenu(menu);
        if (row > 0) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "根据ID更新菜单目录")
    @PutMapping()
    public Result updateMenuByMenuId(@RequestBody SysMenu sysMenu) {
        sysMenu.setGmtModified(new Date());
        return ResultFactory.successOf(this.sysMenuService.updateMenuByMenuId(sysMenu));
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "根据ID删除菜单目录")
    @DeleteMapping("/{menuId}")
    public Result deleteMenuByMenuId(@PathVariable("menuId") Integer menuId) {
        return ResultFactory.successOf(this.sysMenuService.deleteMenuByMenuId(menuId));
    }

    @ApiOperation(value = "获取菜单目录", notes = "获取树形菜单")
    @GetMapping()
    public Result getMenuList() {
        List<SysMenu> menuList = sysMenuService.selectMenuList();
        return ResultFactory.successOf(listToTree(menuList, "0"));
    }

    @ApiOperation(value = "根据菜单编号查询菜单实体", notes = "根据id获取树形菜单")
    @GetMapping("/{menuId}")
    public Result getMenuByMenuId(@PathVariable Integer menuId) {
        return ResultFactory.successOf(sysMenuService.getMenuByMenuId(menuId));
    }

    @ApiOperation(value = "根据角色编号查找菜单-树形菜单")
    @GetMapping("/role/{roleId}")
    public Result getMenuListTreeByRoleId(@PathVariable Long roleId) {
        List<SysMenu> menuList = sysMenuService.getMenuListByRoleId(roleId);
        return ResultFactory.successOf(listToTree(menuList, "0"));
    }

    @ApiOperation(value = "根据角色编号查找菜单-列表", notes = "parent为null或true时，查询全部；false为父节点和子节点结构分离")
    @GetMapping("/role/{roleId}/list/{parent}")
    public Result getMenuListByRoleId(@PathVariable("roleId") Long roleId,
                                      @ApiParam(name = "parent", value = "false为父节点和子节点结构分离", required = false) @PathVariable(value = "parent", required = false) Boolean parent) {
        List<SysMenu> list = sysMenuService.getMenuListByRoleId(roleId);
        final Comparator<SysMenu> comparator = comparing(SysMenu::getOrderNum, nullsLast(naturalOrder()));
        list.sort(comparator);
        if (null == parent || parent) {
            return ResultFactory.successOf(list);
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("parent", list.stream().filter(x -> x.getParentId() == 0).collect(Collectors.toList()));
        map.put("child", list.stream().filter(x -> x.getParentId() != 0).collect(Collectors.toList()));
        return ResultFactory.successOf(map);
    }

    @ApiOperation(value = "根据用户编号查询菜单列表")
    @GetMapping("/user/{userId}")
    public Result getMenuListByUserId(@PathVariable Integer userId) {
        List<SysMenu> menuList = sysMenuService.getMenuListByUserId(userId);
        return ResultFactory.successOf(listToTree(menuList, "0"));
    }

    @ApiOperation(value = "获取当前用户菜单")
    @GetMapping("/user")
    public Result getMenuListByUser(@AccessTokenUser DefaultSysUser user) {
        Integer userId = user.getUserId();
        if (Objects.nonNull(userId)) {
            List<SysMenu> menuList = this.sysMenuService.getMenuListByUser(userId);
            if (menuList != null) {
                final Comparator<SysMenu> comparator = comparing(SysMenu::getOrderNum, nullsLast(naturalOrder()));
                menuList.sort(comparator);
                return ResultFactory.successOf(listToTrees(menuList, "0"));
            } else {
                return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR, "权限不足");
            }
        } else {
            return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR, "请求失败");
        }
    }

    /**
     * @return
     * @description: 根据用户ID获取用户的二级菜单
     * @author ww
     */
    @ApiOperation(value = "获取当前用户的二级菜单", notes = "获取当前登录用户的二级菜单")
    @GetMapping("/getSecondaryMenu")
    public Result getSecondaryMenu(@AccessTokenUser DefaultSysUser sysUser) {
        Integer userId = sysUser.getUserId();
        if (Objects.nonNull(userId)) {
            List<SysMenu> menuList = this.sysMenuService.getSecondaryMenu(userId);
            if (menuList != null) {
                return ResultFactory.successOf(menuList);
            } else {
                return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR, "权限不足");
            }
        } else {
            return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR, "请求失败");
        }
    }

    @ApiOperation(value = "获取指定菜单除根节点0以外的父节点ID集合")
    @GetMapping("/{menuId}/parentIds")
    public Result getMenuListByUser(@PathVariable Integer menuId) {
        List<SysMenu> menuList = sysMenuService.selectMenuList();
        Integer parentId = sysMenuService.selectById(menuId).getParentId();
        List<Integer> parentIdList = Lists.newArrayList();
        getParentIdList(menuList, parentId, parentIdList);
        return ResultFactory.successOf(parentIdList);
    }

    public void getParentIdList(List<SysMenu> menuList, Integer menuId, List<Integer> parentIdList) {
        for (SysMenu sysMenu : menuList) {
            if (menuId.equals(sysMenu.getMenuId())) {
                parentIdList.add(menuId);
                getParentIdList(menuList, sysMenu.getParentId(), parentIdList);
            }
        }
    }



//    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "初始化菜单树只包含两级   0：目录   1：菜单")
    @PostMapping("/init")
    public Result initMenuTree(@RequestBody List<MenuNode> menuNodeList) {
        return sysMenuService.initMenuTree(menuNodeList);
    }
}
