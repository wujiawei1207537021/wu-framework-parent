package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;

public interface DatabaseApplication {
    /**
     * describe 查询数据库表
     *
     * @param instanceId 数据库服务器id
     * @param schema             数据库名称
     * @return
     * @author Jia wei Wu
     * @date 2022/1/29 22:57
     **/
    Result listTable(String instanceId, String schema);

    /**
     * 查看表字段
     *
     * @param instanceId 数据库服务器id
     * @param schema             数据库名称
     * @param tableName          数据库表
     * @return
     */
    Result listLazyColumn(String instanceId, String schema, String tableName);
}
