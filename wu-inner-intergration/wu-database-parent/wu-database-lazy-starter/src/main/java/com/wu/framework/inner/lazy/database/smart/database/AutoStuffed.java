package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;

/**
 * describe : 自动填充数据 塞入
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/19 9:22 下午
 */
public interface AutoStuffed {

    /**
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param table 表
     * @param num   数量
     */
    void stuffed(LazyTableInfo table, Long num);

    /**
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param table 表
     * @param num   数量
     */
    default void stuffed(String schema, String table, Long num) {
        stuffed(new LazyTableInfo().setTableSchema(schema).setTableName(table), num);
    }


    /**
     * describe 根据class填充数据
     *
     * @param tableClass 表class
     * @param num        数量
     * @return
     * @author Jia wei Wu
     * @date 2022/1/19 9:28 下午
     **/
    <T> void stuffed(Class<T> tableClass, Long num);

    /**
     * describe 根据表明创建出Java文件
     *
     * @param schema 数据库
     * @param tableName  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    void stuffedJava(String schema, String tableName);

}
