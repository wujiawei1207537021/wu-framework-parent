package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.smart.database.AutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * describe : 聪明懒人的操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/19 9:25 下午
 */
public class LazySmartLazyOperation implements SmartLazyOperation {

    private final AutoStuffed autoStuffed;
    private final Perfect perfect;

    public LazySmartLazyOperation(AutoStuffed autoStuffed, Perfect perfect) {
        this.autoStuffed = autoStuffed;
        this.perfect = perfect;
    }

    /**
     * description 数据库数据存储到sql文件
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    @Override
    public void saveSqlFile(String nameDatabase) {

        try {
            perfect.saveSqlFile(nameDatabase);
        } catch (IOException | ProcessException | MethodParamFunctionException | ExecutionException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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
    public void stuffed(String schema, String table, Long num) {
        autoStuffed.stuffed(schema, table, num);
    }

    @Override
    public void stuffed(Class table, Long num) {
        autoStuffed.stuffed(table, num);
    }

    /**
     * 塞入所有数据
     *
     * @param num
     */
    @Override
    public void stuffedAll(Long num) {
        for (LazyDatabase showDatabase : perfect.showDatabases()) {
            System.out.println("计划执行数据库填充" + showDatabase.getDatabase());
            for (LazyTableInfo showTable : perfect.showTables(showDatabase.getDatabase())) {
                autoStuffed.stuffed(showTable, num);
            }
        }
    }

    /**
     * describe 根据表明创建出Java文件
     *
     * @param schema 数据库
     * @param table  表名
     * @return
     * @author Jia wei Wu
     * @date 2022/1/23 12:23 上午
     **/
    @Override
    public void stuffedJava(String schema, String table) {
        autoStuffed.stuffedJava(schema, table);
    }

    /**
     * 查询数据库中的表
     *
     * @param schema 数据库
     * @return List<LazyTableInfo> 数据库中的表
     */
    @Override
    public List<LazyTableInfo> showTables(String schema) {
        return perfect.showTables(schema);
    }
}
