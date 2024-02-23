package com.wu.smart.acw.server.controller;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelPointContextHolder;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.persistence.map.DbMap;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwTableConsoleApplication;
import com.wu.smart.acw.server.application.command.AcwTableColumnConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowBatchConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowConsoleCommand;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "表搜索引擎")
@EasyController("/table/console")
public class AcwTableConsoleController {


    private final AcwTableConsoleApplication acwTableConsoleApplication;

    public AcwTableConsoleController(AcwTableConsoleApplication acwTableConsoleApplication) {
        this.acwTableConsoleApplication = acwTableConsoleApplication;
    }


    /**
     * describe 执行sql语句
     *
     * @param acwTableConsoleCommand 表查询参数
     * @return 分页返回查询结果
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("执行sql语句")
    @PostMapping("/execute/table/sql/statement")
    public Result<LazyPage<DbMap>> tableSqlConsole(@RequestBody AcwTableConsoleCommand acwTableConsoleCommand) {
        return acwTableConsoleApplication.tableSqlConsole(acwTableConsoleCommand);
    }

    /**
     * 获取表指定列数据列表
     *
     * @param acwTableColumnConsoleCommand
     * @return
     */
    @ApiOperation("获取表指定列数据列表")
    @PostMapping("/execute/table/sql/statement/column")
    public Result<LazyPage<String>> tableColumnSqlConsole(@RequestBody AcwTableColumnConsoleCommand acwTableColumnConsoleCommand) {
        return acwTableConsoleApplication.tableColumnSqlConsole(acwTableColumnConsoleCommand);
    }

    /**
     * describe 导出执行sql语句
     *
     * @param
     * @param acwTableConsoleCommand
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("导出执行sql语句")
    @EasyExcel()
    @PostMapping("/execute/table/sql/statement/export")
    public Object tableSqlConsoleExport(@RequestBody AcwTableConsoleCommand acwTableConsoleCommand) {

        // 解析数据库表
        String fileName = acwTableConsoleCommand.getTableName();
        fileName = fileName.replace(NormalUsedString.SEMICOLON, "");
        EasyExcelPoint easyExcelPoint = new EasyExcelPoint();
        easyExcelPoint.setFileName(fileName);
        DynamicEasyExcelPointContextHolder.push(easyExcelPoint);
        Result<LazyPage<DbMap>> result = acwTableConsoleApplication.tableSqlConsole(acwTableConsoleCommand);
        return result.getData().getRecord();
    }

    /**
     * describe 存储表行数据
     *
     * @param
     * @param acwTableRowConsoleCommand
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("存储表数据")
    @PostMapping("/execute/upsert/table/sql")
    public Result tableRowStory(@RequestBody AcwTableRowConsoleCommand acwTableRowConsoleCommand) {
        return acwTableConsoleApplication.tableRowStory(acwTableRowConsoleCommand);
    }

    /**
     * describe 删除表行数据
     *
     * @param
     * @param acwTableRowConsoleCommand
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("删除表行数据")
    @PostMapping("/execute/delete/table/sql")
    public Result tableRowDelete(@RequestBody AcwTableRowConsoleCommand acwTableRowConsoleCommand) {
        return acwTableConsoleApplication.tableRowDelete(acwTableRowConsoleCommand);
    }

    /**
     * describe 批量删除表行数据
     *
     * @param
     * @param acwTableRowBatchConsoleCommand 批量参数
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("批量删除表行数据")
    @PostMapping("/execute/batch/delete/table/sql")
    public Result tableRowBatchDelete(@RequestBody AcwTableRowBatchConsoleCommand acwTableRowBatchConsoleCommand) {
        return acwTableConsoleApplication.tableRowBatchDelete(acwTableRowBatchConsoleCommand);
    }

    /**
     * describe 导出执行sql语句
     *
     * @param acwTableConsoleCommand 查询参数
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("导出执行sql upsert语句")
    @EasyFile(fileName = "导出upsert-sql", suffix = "sql")
    @PostMapping("/execute/upsert/table/sql/statement/export")
    public String tableSqlConsoleUpsertExport(
            @RequestBody AcwTableConsoleCommand acwTableConsoleCommand) {

        // 解析数据库表

        String fileName = acwTableConsoleCommand.getTableName();
        EasyFilePoint filePoint = new EasyFilePoint();
        filePoint.setFileName(fileName);
        filePoint.setSuffix("sql");

        DynamicEasyFileContextHolder.push(filePoint);
        return acwTableConsoleApplication.tableSqlConsoleUpsertExport(acwTableConsoleCommand);
    }
    /**
     * describe 导出结果为MD
     *
     * @param acwTableConsoleCommand 查询参数
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("导出结果为MD")
    @EasyFile(fileName = "导出MD", suffix = "md")
    @PostMapping("/execute/md/table/sql/statement/export")
    public String tableSqlConsoleMdExport(
            @RequestBody AcwTableConsoleCommand acwTableConsoleCommand) {

        // 解析数据库表

        String fileName = acwTableConsoleCommand.getTableName();
        EasyFilePoint filePoint = new EasyFilePoint();
        filePoint.setFileName(fileName);
        filePoint.setSuffix("md");

        DynamicEasyFileContextHolder.push(filePoint);
        return acwTableConsoleApplication.tableSqlConsoleMdExport(acwTableConsoleCommand);
    }


}
