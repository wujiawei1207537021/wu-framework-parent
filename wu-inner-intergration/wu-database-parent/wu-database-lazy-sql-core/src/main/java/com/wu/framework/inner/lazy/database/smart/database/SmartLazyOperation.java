package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;

import java.util.List;
import java.util.function.Consumer;

/**
 * 聪明懒人的操作
 * 包含数据库自动填充、sql脚本自动获取解析
 *
 * @author Jia wei Wu
 */
public interface SmartLazyOperation extends SmartLazyOperationSaveSql, SmartLazyOperationAutoStuffed {


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
    @Override
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
    @Override
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
     * @param schema    数据库
     * @param tableName 表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    @Override
    void stuffedJava(String schema, String tableName);


    /**
     * 查询所有的数据库
     *
     * @return
     */
    List<LazyDatabase> showDatabases();

    /**
     * 查询数据库中的表
     *
     * @param schema 数据库
     * @return List<LazyTableInfo> 数据库中的表
     */
    List<LazyTableInfo> showTables(String schema);


    /**
     * 查询数据库中的表的数据
     *
     * @param tableName 表名称
     * @param consumer  返回的数据
     * @return void
     */
    void scrollTableData(String tableName, Consumer<LazyPage<EasyHashMap>> consumer);

    /**
     * 查询数据库中的表的数据
     *
     * @param tableName 表名称
     * @param consumer  返回的数据
     * @return void
     */
    void scrollTableData(String tableName, LazyPage lazyPage, Consumer<LazyPage<EasyHashMap>> consumer);
}
