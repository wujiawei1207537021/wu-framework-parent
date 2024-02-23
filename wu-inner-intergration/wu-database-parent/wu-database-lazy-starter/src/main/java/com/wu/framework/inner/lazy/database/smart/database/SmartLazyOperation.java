package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;

import java.util.List;

/**
 * 聪明懒人的操作
 *
 * @author wujiawei
 */
public interface SmartLazyOperation {

    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 数据库数据存储到sql文件
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    void saveSqlFile(String nameDatabase);


    /**
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                     以下是方法是自动塞入                                                                        ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     */
    /**
     * 自动填充数据
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param schema 数据库
     * @param table  表
     * @param num    数量
     */
    void stuffed(String schema, String table, Long num);

    /**
     * 自动填充数据
     * SELECT
     * *
     * FROM
     * information_schema.COLUMNS
     * WHERE
     * TABLE_SCHEMA = 'lazy'
     * AND TABLE_NAME = 'sys_user';
     *
     * @param table class 对应数据库结构的class
     * @param num   数量
     */
    void stuffed(Class table, Long num);

    /**
     * 塞入所有数据
     *
     * @param num
     */
    void stuffedAll(Long num);


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

    /**
     * 查询数据库中的表
     *
     * @param schema 数据库
     * @return List<LazyTableInfo> 数据库中的表
     */
    List<LazyTableInfo> showTables(String schema);
}
