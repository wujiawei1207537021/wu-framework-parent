package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.Collection;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2023/1/1 23:31
 */
public abstract class AbstractSmartLazyOperationSaveSql implements SmartLazyOperationSaveSql {


    /**
     * describe 获取 LazyLambdaStream
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 23:33
     **/
    protected abstract LazyLambdaStream getLazyLambdaStream();


    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 数据库数据存储到sql文件(删除表后、创建表 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    @Override
    public File saveSqlFile(String nameDatabase) {
        return null;
    }

    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * description 柔和形 数据库数据存储到sql文件(表存在不删除 数据使用upsert)
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    @Override
    public File saveSoftSqlFile(String nameDatabase) {
        return null;
    }

    /**
     * describe  导出增量式更新数据
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jia wei Wu
     * @date 2022/4/9 22:57
     **/
    @Override
    public File saveUpsertSqlFile(String nameDatabase) {
        return null;
    }

    /**
     * 查询出表 tableName 中所有的数据
     *
     * @param schemaName 数据库
     * @param tableName  表
     * @return String insert sql
     */
    @Override
    public String exportInsertSql(String schemaName, String tableName) {
        // 滚动查询
        // 将结果转换成insert 语句
        StringBuilder stringBuilder = new StringBuilder();
        getLazyLambdaStream().scroll(null, EasyHashMap.class, "select * from {0}.{1}", page -> {
            Collection<EasyHashMap> record = page.getRecord();
            if (!ObjectUtils.isEmpty(record)) {
                String insert = LazyTableUpsertConverterFactory.insert(record);
                stringBuilder.append(insert);
            }
        }, schemaName, tableName);
        return stringBuilder.toString();
    }

    /**
     * 查询出表 tableName 中所有的数据 upsert
     *
     * @param schemaName 数据库
     * @param tableName  表
     * @return String insert sql
     */
    @Override
    public String exportUpsertSql(String schemaName, String tableName) {
        // 滚动查询
        // 将结果转换成upsert 语句
        StringBuilder stringBuilder = new StringBuilder();
        getLazyLambdaStream().scroll(null, EasyHashMap.class, "select * from {0}.{1}", page -> {
            Collection<EasyHashMap> record = page.getRecord();
            if (!ObjectUtils.isEmpty(record)) {
                String insert = LazyTableUpsertConverterFactory.upsert(record);
                stringBuilder.append(insert);
            }
        }, schemaName, tableName);
        return stringBuilder.toString();
    }
}
