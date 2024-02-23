package com.wu.smart.acw.server.service;

import com.wu.framework.response.Result;

public interface DatabaseService {
    /**
     * describe 查询数据库表
     *
     * @param databaseServerId 数据库服务器id
     * @param schema           数据库名称
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:57
     **/
    Result listTable(Long databaseServerId, String schema);

    /**
     * 查看表字段
     *
     * @param databaseServerId 数据库服务器id
     * @param schema           数据库名称
     * @param tableName        数据库表
     * @return
     */
    Result listLazyColumn(Long databaseServerId, String schema, String tableName);
}
