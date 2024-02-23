package com.wu.framework.authorization.platform.controller;

import com.wu.framework.authorization.annotation.AccessTokenUser;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.platform.application.MenuApplication;
import com.wu.framework.authorization.platform.application.command.MenuCommand;
import com.wu.framework.authorization.platform.application.dto.MenuTreeDTO;
import com.wu.framework.authorization.platform.infrastructure.entity.MenuDO;
import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
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
@Tag(name = "提供者")
@EasyController("/menu")
public class MenuProvider
//        extends AbstractLazyCrudProvider<MenuDO, Long>
{

    @Autowired
    private MenuApplication menuApplication;


    /**
     * describe  批量存储 增量更新
     *
     * @param menuCommandList 实体对象
     * @return Result
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/upsert")
    public  Result upsert(@RequestBody List<MenuCommand> menuCommandList) {
        return menuApplication.upsert(menuCommandList);
    }



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
    public Result<Menu> story(@RequestBody MenuCommand menuCommand) {
        return menuApplication.save(menuCommand);
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
    public Result<Menu> updateOne(@RequestBody MenuCommand menuCommand) {
        return menuApplication.update(menuCommand);
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
    public Result<Menu> findOne(@ModelAttribute MenuCommand menuCommand) {
        return menuApplication.findOne(menuCommand);
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
    public Result<List<Menu>> findList(@ModelAttribute MenuCommand menuCommand) {
        return menuApplication.findList(menuCommand);
    }
    /**
     * describe 查询菜单树
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/

    @GetMapping("/findTree")
    public Result<List<MenuTreeDTO>> findTree(@ModelAttribute MenuCommand menuCommand) {
        return menuApplication.findTree(menuCommand);
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
    public Result<Menu> delete(@ModelAttribute MenuCommand menuCommand) {
        return menuApplication.delete(menuCommand);
    }
}