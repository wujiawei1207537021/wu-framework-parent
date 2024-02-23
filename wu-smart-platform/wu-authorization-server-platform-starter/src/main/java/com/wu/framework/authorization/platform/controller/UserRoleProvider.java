package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.UserRoleApplication;
import com.wu.framework.authorization.platform.application.command.UserRoleCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.UserRoleDO;
import com.wu.framework.authorization.platform.model.user.role.UserRole;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 用户角色关联关系
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "用户角色关联关系提供者")
@EasyController("/user/role")
public class UserRoleProvider extends AbstractLazyCrudProvider<UserRoleDO,UserRoleDO, Long> {

    @Autowired
    private UserRoleApplication userRoleApplication;

    /**
     * describe 新增用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<UserRole> story(@RequestBody UserRoleCommand userRoleCommand) {
        return userRoleApplication.save(userRoleCommand);
    }

    /**
     * describe 更新用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PutMapping("/updateOne")
    public Result<UserRole> updateOne(@RequestBody UserRoleCommand userRoleCommand) {
        return userRoleApplication.update(userRoleCommand);
    }

    /**
     * describe 查询单个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @GetMapping("/findOne")
    public Result<UserRole> findOne(@ModelAttribute UserRoleCommand userRoleCommand) {
        return userRoleApplication.findOne(userRoleCommand);
    }

    /**
     * describe 查询多个用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @GetMapping("/findList")
    public Result<List<UserRole>> findList(@ModelAttribute UserRoleCommand userRoleCommand) {
        return userRoleApplication.findList(userRoleCommand);
    }

    /**
     * describe 删除用户角色关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @DeleteMapping("/delete")
    public Result<UserRole> delete(@ModelAttribute UserRoleCommand userRoleCommand) {
        return userRoleApplication.delete(userRoleCommand);
    }
}