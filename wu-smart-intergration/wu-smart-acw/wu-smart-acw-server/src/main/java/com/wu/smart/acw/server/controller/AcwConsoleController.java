package com.wu.smart.acw.server.controller;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.endpoint.EasyFilePoint;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.stereotype.EasyFile;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelPointContextHolder;
import com.wu.framework.easy.excel.toolkit.DynamicEasyFileContextHolder;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.ConsoleApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * describe : 控制台控制器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 14:07
 */
@Tag(name = "控制台控制器")
@EasyController("/console")
public class AcwConsoleController {

    private final ConsoleApplication consoleApplication;

    public AcwConsoleController(ConsoleApplication consoleApplication) {
        this.consoleApplication = consoleApplication;
    }


    /**
     * describe 执行sql语句
     *
     * @param
     * @param instanceId
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("执行sql语句")
    @GetMapping("/execute/sql/statement")
    public Result<List<List<LinkedHashMap>>> sqlConsole(
            @RequestParam("instanceId") String instanceId,
            @RequestParam("schemaName") String schemaName,
            @RequestParam("sql") String sql) {
        return consoleApplication.sqlConsole(instanceId, schemaName, sql);
    }

    /**
     * describe 导出执行sql语句
     *
     * @param
     * @param instanceId
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("导出执行sql语句")
    @EasyExcel()
    @GetMapping("/execute/sql/statement/export")
    public Object sqlConsoleExport(
            @RequestParam("instanceId") String instanceId,
            @RequestParam("schemaName") String schemaName,
            @RequestParam("sql") String sql) {

        // 解析数据库表

        String fileName = sql;
        String upperCaseSql = sql.toUpperCase(Locale.ROOT);
        fileName = fileName.trim().substring(upperCaseSql.indexOf("FROM") + 4, upperCaseSql.contains("WHERE") ? upperCaseSql.indexOf("WHERE") : sql.length());
        fileName = fileName.replace(NormalUsedString.SEMICOLON, "");
        EasyExcelPoint easyExcelPoint = new EasyExcelPoint();
        easyExcelPoint.setFileName(fileName);

        DynamicEasyExcelPointContextHolder.push(easyExcelPoint);
        Result<List<List<LinkedHashMap>>> result = consoleApplication.sqlConsole(instanceId, schemaName, sql);
        return result.getData();
    }

    /**
     * describe 导出执行sql语句
     *
     * @param
     * @param instanceId
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:44
     **/
    @ApiOperation("导出执行sql upsert语句")
    @EasyFile(fileName = "导出upsert-sql", suffix = "sql")
    @GetMapping("/execute/upsert/sql/statement/export")
    public String sqlConsoleUpsertExport(
            @RequestParam("instanceId") String instanceId,
            @RequestParam("schemaName") String schemaName,
            @RequestParam("sql") String sql) {

        // 解析数据库表

        String fileName = sql;
        String upperCaseSql = sql.toUpperCase(Locale.ROOT);
        fileName = fileName.trim().substring(upperCaseSql.indexOf("FROM") + 4, upperCaseSql.contains("WHERE") ? upperCaseSql.indexOf("WHERE") : sql.length());
        fileName = fileName.replace(NormalUsedString.SEMICOLON, "");
        EasyFilePoint filePoint = new EasyFilePoint();
        filePoint.setFileName(fileName);

        DynamicEasyFileContextHolder.push(filePoint);
        String upsertSql = consoleApplication.sqlConsoleUpsertExport(instanceId, schemaName, sql);
        return upsertSql;
    }

}
