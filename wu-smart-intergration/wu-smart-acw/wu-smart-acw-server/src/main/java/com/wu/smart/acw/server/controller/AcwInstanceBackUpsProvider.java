package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwInstanceBackUpsApplication;
import com.wu.smart.acw.server.application.command.AcwInstanceBackUpsCommand;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "数据库备份信息提供者")
@EasyController("/lazy/database/instance/back/ups")
public class AcwInstanceBackUpsProvider {

    @Autowired
    private AcwInstanceBackUpsApplication acwInstanceBackUpsApplication;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @PostMapping("/story")
    public Result<DatabaseInstanceBackUps> story(@RequestBody AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.story(acwInstanceBackUpsCommand);
    }

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @PutMapping("/updateOne")
    public Result<DatabaseInstanceBackUps> updateOne(@RequestBody AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.updateOne(acwInstanceBackUpsCommand);
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @GetMapping("/findOne")
    public Result<DatabaseInstanceBackUps> findOne(@ModelAttribute AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.findOne(acwInstanceBackUpsCommand);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @GetMapping("/findList")
    public Result<List<DatabaseInstanceBackUps>> findList(@ModelAttribute AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.findList(acwInstanceBackUpsCommand);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<DatabaseInstanceBackUps>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                              @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.findPage(size, current, acwInstanceBackUpsCommand);
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @DeleteMapping("/remove")
    public Result<DatabaseInstanceBackUps> remove(@ModelAttribute AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        return acwInstanceBackUpsApplication.remove(acwInstanceBackUpsCommand);
    }


    /**
     * describe  数据库实例备份
     *
     * @param acwInstanceBackUpsCommand 实例
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("数据库实例备份")
    @PatchMapping("/backUps")
    public void backUps(@RequestBody AcwInstanceBackUpsCommand acwInstanceBackUpsCommand) {
        acwInstanceBackUpsApplication.backUps(acwInstanceBackUpsCommand.getInstanceId());
    }

}