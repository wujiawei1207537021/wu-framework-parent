package com.wu.smart.acw.server.controller;

import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwTableAutoStuffedRecordUo;
import com.wu.smart.acw.server.application.AcwTableAutoStuffedRecordApplication;
import com.wu.smart.acw.server.application.command.AcwTableAutoStuffedRecordCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordQueryListCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordStoryCommand;
import com.wu.smart.acw.server.application.command.acw.table.auto.stuffed.record.AcwTableAutoStuffedRecordUpdateCommand;
import com.wu.smart.acw.server.application.dto.AcwTableAutoStuffedRecordDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "ACW-数据库表数据自动填充")
@EasyController("/database/table/auto/stuffed/record")
public class AcwTableAutoStuffedRecordController {

    private final AcwTableAutoStuffedRecordApplication acwTableAutoStuffedRecordApplication;

    public AcwTableAutoStuffedRecordController(AcwTableAutoStuffedRecordApplication acwTableAutoStuffedRecordApplication) {
        this.acwTableAutoStuffedRecordApplication = acwTableAutoStuffedRecordApplication;
    }

    /**
     * describe 查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    @Operation(summary = "查询多个数据库表填充记录")
    @GetMapping("/findList")
    public Result<List<AcwTableAutoStuffedRecordDTO>> findList(@ModelAttribute AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand) {
        return acwTableAutoStuffedRecordApplication.findList(acwTableAutoStuffedRecordQueryListCommand);
    }

    /**
     * describe 分页查询多个数据库表填充记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/25 02:15 下午
     **/

    @Operation(summary = "分页查询多个数据库表填充记录")
    @GetMapping("/findPage")
    public Result<LazyPage<AcwTableAutoStuffedRecordDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                                   @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AcwTableAutoStuffedRecordQueryListCommand acwTableAutoStuffedRecordQueryListCommand) {
        return acwTableAutoStuffedRecordApplication.findPage(size, current, acwTableAutoStuffedRecordQueryListCommand);
    }

    /**
     * describe  根据更新
     *
     * @param acwTableAutoStuffedRecordUpdateCommand 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("更新")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody AcwTableAutoStuffedRecordUpdateCommand acwTableAutoStuffedRecordUpdateCommand) {
        return acwTableAutoStuffedRecordApplication.update(acwTableAutoStuffedRecordUpdateCommand);
    }

    /**
     * describe  根据主键ID 删除
     *
     * @param id 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable("id") String id) {
        return acwTableAutoStuffedRecordApplication.deleteById(id);
    }

    ;

    /**
     * describe  根据主键ID 批量删除
     *
     * @param ids 主键ID
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("根据主键ID 删除")
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteById(@RequestBody List<String> ids) {
        return acwTableAutoStuffedRecordApplication.batchDeleteById(ids);
    }

    ;

    /**
     * describe  新增
     *
     * @param acwTableAutoStuffedRecordStoryCommand 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<AcwTableAutoStuffedRecordUo> save(AcwTableAutoStuffedRecordStoryCommand acwTableAutoStuffedRecordStoryCommand) {
        return acwTableAutoStuffedRecordApplication.save(acwTableAutoStuffedRecordStoryCommand);
    }

    /**
     * 批量新增
     *
     * @param acwTableAutoStuffedRecordCommand
     * @return
     */
    @PostMapping("/batchSave")
    public Result batchSave(@RequestBody AcwTableAutoStuffedRecordCommand acwTableAutoStuffedRecordCommand) {
        return acwTableAutoStuffedRecordApplication.batchSave(acwTableAutoStuffedRecordCommand);
    }

    public static void main(String[] args) throws IOException {
        FileUtil.aggregateAllFilesOnFolder("/Users/wujiawei/IdeaProjects/wu-framework-parent/wu-smart-intergration/wu-smart-acw/wu-smart-acw-server/src/main/java/com/wu/smart/acw/server/controller/pdf",
                true, ".pdf");
    }
}
