package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.platform.application.RoleApplication;
import com.wu.framework.authorization.platform.application.command.RoleCommand;
import com.wu.framework.authorization.platform.infrastructure.entity.RoleDO;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 角色
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "角色提供者")
@EasyController("/role")
public class RoleProvider extends AbstractLazyCrudProvider<RoleDO,RoleDO, Long> {

    @Autowired
    private RoleApplication roleApplication;

    /**
     * describe 新增角色
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @PostMapping("/story")
    public Result<Role> story(@RequestBody RoleCommand roleCommand) {
        return roleApplication.save(roleCommand);
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
    @PutMapping("/updateOne")
    public Result<Role> updateOne(@RequestBody RoleCommand roleCommand) {
        return roleApplication.update(roleCommand);
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

    @GetMapping("/findOne")
    public Result<Role> findOne(@ModelAttribute RoleCommand roleCommand) {
        return roleApplication.findOne(roleCommand);
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

    @GetMapping("/findList")
    public Result<List<Role>> findList(@ModelAttribute RoleCommand roleCommand) {
        return roleApplication.findList(roleCommand);
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
    @DeleteMapping("/delete")
    public Result<Role> delete(@ModelAttribute RoleCommand roleCommand) {
        return roleApplication.delete(roleCommand);
    }
}