package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/2 14:10
 */
public interface ConsoleApplication {

    /**
     * describe 执行sql语句
     *
     * @param instanceId 数据库实例Id
     * @param schemaName 数据库
     * @param sql        执行的sql
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/10/7 19:47
     **/
    Result<List<List<LinkedHashMap>>> sqlConsole(String instanceId, String schemaName, String sql);

    /***
     * 导出执行sql upsert语句
     * @param instanceId
     * @param schemaName
     * @param sql
     * @return
     */
    String sqlConsoleUpsertExport(String instanceId, String schemaName, String sql);
}
