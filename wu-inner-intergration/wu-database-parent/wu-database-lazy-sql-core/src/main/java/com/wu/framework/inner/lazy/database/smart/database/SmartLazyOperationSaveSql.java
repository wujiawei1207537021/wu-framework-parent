package com.wu.framework.inner.lazy.database.smart.database;

import java.io.File;

/**
 * describe : 存储sql的懒人操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/1 22:17
 */
public interface SmartLazyOperationSaveSql {


    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 数据库数据存储到sql文件(删除表后、创建表 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    File saveSqlFile(String nameDatabase);

    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 柔和形 数据库数据存储到sql文件(表存在不删除 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    File saveSoftSqlFile(String nameDatabase);


    /**
     * describe  导出增量式更新数据
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jia wei Wu
     * @date 2022/4/9 22:57
     **/
    File saveUpsertSqlFile(String nameDatabase);

    /**
     * 查询出表 tableName 中所有的数据 insert
     *
     * @param schemaName 数据库
     * @param tableName  表
     * @return String insert sql
     */
    String exportInsertSql(String schemaName, String tableName);

    /**
     * 查询出表 tableName 中所有的数据 upsert
     *
     * @param schemaName 数据库
     * @param tableName  表
     * @return String insert sql
     */
    String exportUpsertSql(String schemaName, String tableName);
}
