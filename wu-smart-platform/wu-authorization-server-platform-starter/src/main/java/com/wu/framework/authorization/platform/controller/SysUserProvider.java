package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.annotation.AccessTokenUser;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.platform.application.SysUserApplication;
import com.wu.framework.authorization.platform.application.command.SysUserCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.SysUserDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "系统用户服务提供者")
@EasyController("/sys/user")
public class SysUserProvider extends AbstractLazyCrudProvider<SysUserDO,SysUserDO, Long> {

    @Autowired
    private SysUserApplication sysUserApplication;

    /**
     * describe 新增
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<SysUser> story(@RequestBody SysUserCommand sysUserCommand) {
        return sysUserApplication.save(sysUserCommand);
    }

    /**
     * describe 更新
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PutMapping("/updateOne")
    public Result<SysUser> updateOne(@RequestBody SysUserCommand sysUserCommand) {
        return sysUserApplication.update(sysUserCommand);
    }

    /**
     * describe 查询单个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @GetMapping("/findOne")
    public Result<SysUser> findOne(@ModelAttribute SysUserCommand sysUserCommand) {
        return sysUserApplication.findOne(sysUserCommand);
    }

    /**
     * describe 查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @GetMapping("/findList")
    public Result<List<SysUser>> findList(@ModelAttribute SysUserCommand sysUserCommand) {
        return sysUserApplication.findList(sysUserCommand);
    }

    /**
     * describe 导出用户角色信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @EasyExcel(fileName = "导出用户角色信息")
    @GetMapping("/export/List")
    public List<SysUser> exportList(@ModelAttribute SysUserCommand sysUserCommand) {
        return sysUserApplication.findList(sysUserCommand).getData();
    }

    /**
     * describe 查询用户菜单
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    @GetMapping("/findUserMenuList")
    public Result<List<Menu>> findUserMenuList(@Parameter(hidden = true) @AccessTokenUser UserDetails userDetails) {
        return sysUserApplication.findUserMenuList(userDetails);
    }

    /**
     * describe 删除
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @DeleteMapping("/delete")
    public Result<SysUser> delete(@ModelAttribute SysUserCommand sysUserCommand) {
        return sysUserApplication.delete(sysUserCommand);
    }

    /**
     * describe 分页查询多个
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/21 11:16 晚上
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<SysUser>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                              @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute SysUserCommand sysUserCommand) {
        return sysUserApplication.findPage(size, current, sysUserCommand);
    }

}