package com.wu.framework.easy.mysql.binlog.listener.config;


import com.wu.framework.easy.mysql.binlog.listener.serialization.TableInfo;

/**
 * description 数据库表信息适配器
 *
 * @author Jia wei Wu
 * @date 2022/5/13 3:33 下午
 */
public interface TableAdapter {

    /**
     * description 根据tableID获取表信息
     *
     * @param tableId 表ID
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 3:57 下午
     */
    TableInfo getTable(long tableId);

    /**
     * description 本地是否存在表ID信息
     *
     * @param tableId 表id
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 4:03 下午
     */
    Boolean existsTableId(long tableId);

    /**
     * description 缓存表
     *
     * @param schema    数据库
     * @param tableName 表名
     * @param tableId   表ID
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2022/5/13 4:21 下午
     */
    void cacheTable(long tableId, String schema, String tableName);

}
