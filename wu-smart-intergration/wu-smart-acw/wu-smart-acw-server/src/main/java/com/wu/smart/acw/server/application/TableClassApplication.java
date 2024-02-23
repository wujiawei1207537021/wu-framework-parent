package com.wu.smart.acw.server.application;

/**
 * describe: table about class
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/30 00:22
 */
public interface TableClassApplication {

    /**
     * describe 初始化单表
     *
     * @param projectId          项目ID
     * @param instanceId 数据库服务器id
     * @param schema             数据库
     * @return
     * @author Jia wei Wu
     * @date 2022/1/30 00:26
     **/
    void singleTable(Long projectId, String instanceId, String schema);
}
