package com.wu.smart.acw.server.controller;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.dto.JavaModelDto;
import com.wu.smart.acw.server.application.AcwTableApplication;
import com.wu.smart.acw.server.application.command.AcwGenerateLocalJavaCommand;
import com.wu.smart.acw.server.application.command.AcwTableCommand;
import com.wu.smart.acw.server.application.command.acw.table.AcwTableQueryListCommand;
import com.wu.smart.acw.server.application.dto.AcwExportTableStructureExcelDTO;
import com.wu.smart.acw.server.application.dto.AcwTableDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe : 表操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 22:35
 */
@Tag(name = "ACW-数据库表操作")
@EasyController("/table")
public class AcwTableController {
    private final AcwTableApplication acwTableApplication;

    protected AcwTableController(AcwTableApplication acwTableApplication) {
        this.acwTableApplication = acwTableApplication;
    }


    /**
     * describe  查询出所有的数据
     *
     * @param acwTableQueryListCommand 查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     */
    @ApiOperation("查询出所有的数据")
    @GetMapping("/retrieve")
    public Result<List<AcwTableDTO>> retrieveAll(AcwTableQueryListCommand acwTableQueryListCommand) {
        return acwTableApplication.retrieveAll(acwTableQueryListCommand);
    }

    /**
     * describe  查询出分页的数据
     *
     * @param size                     分页大小
     * @param current                  当前页数
     * @param acwTableQueryListCommand 查询参数对象
     * @return Result<T>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @ApiOperation("查询出分页的数据")
    @GetMapping("/retrieve/page")
    public Result<LazyPage<AcwTableDTO>> retrievePage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                      @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current,
                                                      @ModelAttribute AcwTableQueryListCommand acwTableQueryListCommand) {
        return acwTableApplication.retrievePage(size, current, acwTableQueryListCommand);
    }

    /**
     * describe 数据存储
     *
     * @param tableName 表名
     * @param data      数据
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @ApiOperation("数据存储")
    @PostMapping("/data/storage")
    public Result dataStorage(
            @Parameter(description = "表名称") @RequestParam() String tableName, @RequestBody List<EasyHashMap> data) {
        return acwTableApplication.dataStorage(tableName, data);
    }

    /**
     * describe 新增表结构
     *
     * @param acwTableCommand 表
     * @return acwTableColumnUoList 表字段
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/5 17:30
     **/
    @Operation(summary = "新增表结构")
    @PostMapping("/storage")
    public Result storage(
            @RequestBody AcwTableCommand acwTableCommand) {
        return acwTableApplication.storage(acwTableCommand);
    }


    /**
     * describe  根据主键ID 删除
     *
     * @param instanceId instanceId
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @DeleteMapping("/delete/{instanceId}/{schemaName}/{tableName}")
    public Result deleteTable(@Parameter(description = "实例ID") @PathVariable("instanceId") String instanceId,
                              @Parameter(description = "表ID") @PathVariable("schemaName") String schemaName,
                              @Parameter(description = "表ID") @PathVariable("tableName") String tableName) {
        // 删除除表
        return acwTableApplication.deleteTable(instanceId, schemaName, tableName);
    }

    /**
     * describe  批量删除
     *
     * @param acwTableCommandList 表信息
     * @return Result<Long>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @DeleteMapping("/batchDelete")
    public Result batchDeleteTable(@RequestBody List<AcwTableCommand> acwTableCommandList) {
        // 删除除表
        return acwTableApplication.batchDeleteTable(acwTableCommandList);
    }

    /**
     * describe 根据表生成Java对应模型
     *
     * @param instanceId 实例ID
     * @param schemaName schema
     * @param tableName  表名称
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @ApiOperation("根据表生成Java对应模型")
    @GetMapping("/generate/java/model/{instanceId}/{schemaName}/{tableName}")
    public Result<JavaModelDto> generateJavaModel(
            @Parameter(description = "实例ID") @PathVariable("instanceId") String instanceId,
            @Parameter(description = "数据库") @PathVariable("schemaName") String schemaName,
            @Parameter(description = "表") @PathVariable("tableName") String tableName
    ) {
        return acwTableApplication.generateJavaModel(instanceId, schemaName, tableName);
    }

    /**
     * 根据表生成本地Java对应代码
     *
     * @return 返回结果
     */
    @ApiOperation("根据表生成本地Java对应代码")
    @PostMapping("/generate/local/java")
    public Result generateLocalJava(
            @RequestBody AcwGenerateLocalJavaCommand acwGenerateLocalJavaCommand
    ) {
        return acwTableApplication.generateLocalJava(acwGenerateLocalJavaCommand);
    }

    /**
     * describe 下载 insert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @EasyFile(fileName = "导出insert-sql", suffix = "sql")
    @ApiOperation("下载insert-sql")
    @GetMapping("/export/insert/sql")
    public String exportInsertSql(
            @RequestParam String instanceId,
            @RequestParam String schemaName,
            @RequestParam String tableName) {
        return acwTableApplication.exportInsertSql(instanceId, schemaName, tableName);
    }


    /**
     * describe 下载 upsert-sql
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @EasyFile(fileName = "导出upsert-sql", suffix = "sql")
    @ApiOperation("下载upsert-sql")
    @GetMapping("/export/upsert/sql")
    public String exportUpsertSql(
            @RequestParam String instanceId,
            @RequestParam String schemaName,
            @RequestParam String tableName) {
        return acwTableApplication.exportUpsertSql(instanceId, schemaName, tableName);
    }

    /**
     * describe 下载表结构Excel
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @EasyExcel()
    @ApiOperation("下载表结构Excel")
    @GetMapping("/export/table/structure/excel")
    public List<AcwExportTableStructureExcelDTO> exportTableStructureExcel(
            @RequestParam(required = true) String instanceId,
            @RequestParam(required = false) String schemaName,
            @RequestParam(required = false) String tableName) {
        return acwTableApplication.exportTableStructureExcel(instanceId, schemaName, tableName);
    }
    /**
     * describe 下载表结构SQL
     *
     * @param instanceId 数据库实例ID
     * @param schemaName 数据库
     * @param tableName  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:45
     **/
    @EasyFile(suffix = "sql")
    @ApiOperation("下载表结构Sql")
    @GetMapping("/export/table/structure/sql")
    public String exportTableStructureSql(
            @RequestParam(required = true) String instanceId,
            @RequestParam(required = false) String schemaName,
            @RequestParam(required = false) String tableName) {
        return acwTableApplication.exportTableStructureSql(instanceId, schemaName, tableName);
    }
}
