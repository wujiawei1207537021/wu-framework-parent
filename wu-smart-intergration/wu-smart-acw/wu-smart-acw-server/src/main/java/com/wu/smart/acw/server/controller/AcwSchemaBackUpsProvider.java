package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.application.AcwSchemaBackUpsApplication;
import com.wu.smart.acw.server.application.command.AcwSchemaBackUpsCommand;
import com.wu.smart.acw.server.application.dto.AcwSchemaBackUpsDTO;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;
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
 * @date 2023/07/09 03:48 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "数据库备份信息提供者")
@EasyController("/lazy/database/schema/back/ups")
public class AcwSchemaBackUpsProvider {

    @Autowired
    private AcwSchemaBackUpsApplication acwSchemaBackUpsApplication;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @PostMapping("/story")
    public Result<DatabaseSchemaBackUps> story(@RequestBody AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.story(acwSchemaBackUpsCommand);
    }

    /**
     * describe 更新数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @PutMapping("/updateOne")
    public Result<DatabaseSchemaBackUps> updateOne(@RequestBody AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.updateOne(acwSchemaBackUpsCommand);
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @GetMapping("/findOne")
    public Result<DatabaseSchemaBackUps> findOne(@ModelAttribute AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.findOne(acwSchemaBackUpsCommand);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @GetMapping("/findList")
    public Result<List<DatabaseSchemaBackUps>> findList(@ModelAttribute AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.findList(acwSchemaBackUpsCommand);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @GetMapping("/findPage")
    public Result<LazyPage<AcwSchemaBackUpsDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                          @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.findPage(size, current, acwSchemaBackUpsCommand);
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:48 下午
     **/

    @DeleteMapping("/remove")
    public Result<DatabaseSchemaBackUps> remove(@ModelAttribute AcwSchemaBackUpsCommand acwSchemaBackUpsCommand) {
        return acwSchemaBackUpsApplication.remove(acwSchemaBackUpsCommand);
    }


    /**
     * describe  数据库备份
     *
     * @param databaseInstanceBackUpsCommand 数据库
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("数据库备份")
    @PatchMapping("/backUps")
    public Result backUps(@RequestBody AcwSchemaBackUpsCommand databaseInstanceBackUpsCommand) {
        acwSchemaBackUpsApplication.backUps(databaseInstanceBackUpsCommand);
        return ResultFactory.successOf();
    }
}