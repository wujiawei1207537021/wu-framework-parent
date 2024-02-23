package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * describe : 完美操作插件
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/19 9:24 下午
 */
public interface Perfect {
    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 数据库数据存储到sql文件
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    void saveSqlFile(String nameDatabase) throws IOException, ProcessException, MethodParamFunctionException, ExecutionException, InterruptedException, SQLException;

    /**
     * 查询所有的数据库
     *
     * @return
     */
    List<LazyDatabase> showDatabases();

    /**
     * 查询所有的表
     *
     * @return
     */
    List<LazyTableInfo> showTables(String database);
}
