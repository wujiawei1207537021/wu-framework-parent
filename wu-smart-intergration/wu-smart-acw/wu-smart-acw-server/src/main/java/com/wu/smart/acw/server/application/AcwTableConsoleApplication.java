package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.persistence.map.DbMap;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.AcwTableColumnConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowBatchConsoleCommand;
import com.wu.smart.acw.server.application.command.AcwTableRowConsoleCommand;

public interface AcwTableConsoleApplication {

    /***
     * 执行sql语句
     * @param acwTableConsoleCommand 查询参数
     * @return Result<LazyPage<LinkedHashMap>>
     */
    Result<LazyPage<DbMap>> tableSqlConsole(AcwTableConsoleCommand acwTableConsoleCommand);

    /**
     * 导出执行sql语句
     *
     * @param acwTableConsoleCommand 查询参数
     * @return String
     */
    String tableSqlConsoleUpsertExport(AcwTableConsoleCommand acwTableConsoleCommand);

    /**
     * 存储表行数据
     *
     * @param acwTableRowConsoleCommand 查询参数
     * @return Result
     */
    Result tableRowStory(AcwTableRowConsoleCommand acwTableRowConsoleCommand);

    /**
     * 删除表行数据
     *
     * @param acwTableRowConsoleCommand 查询参数
     * @return Result
     */
    Result tableRowDelete(AcwTableRowConsoleCommand acwTableRowConsoleCommand);

    /**
     * describe 批量删除表行数据
     *
     * @param acwTableRowBatchConsoleCommand 查询参数
     * @return Result
     */
    Result tableRowBatchDelete(AcwTableRowBatchConsoleCommand acwTableRowBatchConsoleCommand);

    /**
     * 获取表指定列数据列表
     *
     * @param acwTableColumnConsoleCommand 查询参数
     * @return Result<LazyPage<String>>
     */
    Result<LazyPage<String>> tableColumnSqlConsole(AcwTableColumnConsoleCommand acwTableColumnConsoleCommand);

    /**
     * describe 导出结果为MD
     * @param acwTableConsoleCommand 查询参数
     * @return String
     */
    String tableSqlConsoleMdExport(AcwTableConsoleCommand acwTableConsoleCommand);
}
