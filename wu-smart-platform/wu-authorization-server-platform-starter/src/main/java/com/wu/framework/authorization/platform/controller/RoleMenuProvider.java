package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.RoleMenuApplication;
import com.wu.framework.authorization.platform.application.command.RoleMenuCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleMenuDO;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 角色菜单
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "角色菜单提供者")
@EasyController("/role/menu")
public class RoleMenuProvider extends AbstractLazyCrudProvider<RoleMenuDO,RoleMenuDO, Long> {

    @Autowired
    private RoleMenuApplication roleMenuApplication;

    /**
     * describe 新增角色菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<RoleMenu> story(@RequestBody RoleMenuCommand roleMenuCommand) {
        return roleMenuApplication.save(roleMenuCommand);
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

    @PutMapping("/updateOne")
    public Result<RoleMenu> updateOne(@RequestBody RoleMenuCommand roleMenuCommand) {
        return roleMenuApplication.update(roleMenuCommand);
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

    @GetMapping("/findOne")
    public Result<RoleMenu> findOne(@ModelAttribute RoleMenuCommand roleMenuCommand) {
        return roleMenuApplication.findOne(roleMenuCommand);
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

    @GetMapping("/findList")
    public Result<List<RoleMenu>> findList(@ModelAttribute RoleMenuCommand roleMenuCommand) {
        return roleMenuApplication.findList(roleMenuCommand);
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

    @DeleteMapping("/delete")
    public Result<RoleMenu> delete(@ModelAttribute RoleMenuCommand roleMenuCommand) {
        return roleMenuApplication.delete(roleMenuCommand);
    }
}