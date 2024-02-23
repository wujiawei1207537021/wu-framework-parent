package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.toolkit.LazyCureContextHolder;
import com.wu.framework.inner.lazy.database.smart.database.AbstractSmartLazyOperationSaveSql;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperation;
import com.wu.framework.inner.lazy.database.smart.database.SmartLazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * describe : 聪明懒人的操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/19 9:25 下午
 */
@ConditionalOnBean({SmartLazyOperationAutoStuffed.class, Perfect.class})
public class LazySmartLazyOperation extends AbstractSmartLazyOperationSaveSql implements SmartLazyOperation {


    private final SmartLazyOperationAutoStuffed autoStuffedLazyOperation;
    private final Perfect perfect;
    private final LazyLambdaStream lazyLambdaStream;
    ThreadPoolExecutor LAZY_SMART_EXECUTOR = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(20));

    public LazySmartLazyOperation(SmartLazyOperationAutoStuffed autoStuffedLazyOperation, Perfect perfect, LazyLambdaStream lazyLambdaStream) {
        this.autoStuffedLazyOperation = autoStuffedLazyOperation;
        this.perfect = perfect;
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 获取 LazyLambdaStream
     *
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2023/1/1 23:33
     **/
    @Override
    protected LazyLambdaStream getLazyLambdaStream() {
        return lazyLambdaStream;
    }

    /**
     * description 数据库数据存储到sql文件 包含建表语句，重新创建表会删除表
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    @Override
    public File saveSqlFile(String nameDatabase) {
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        Future<File> submit = LAZY_SMART_EXECUTOR.submit(() -> {
            try {
                if (peek != null) {
                    DynamicLazyDSContextHolder.push(peek);
                }

                // 不做重试操作
                LazyCureContextHolder.push(0);
                return perfect.saveSqlFile(nameDatabase);
            } catch (IOException | ProcessException | MethodParamFunctionException | ExecutionException | SQLException |
                     InterruptedException e) {
                e.printStackTrace();
            } finally {
                DynamicLazyDSContextHolder.clear();
            }
            return null;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        Future<File> submit = LAZY_SMART_EXECUTOR.submit(() -> {
            try {
                if (peek != null) {
                    DynamicLazyDSContextHolder.push(peek);
                }
                // 不做重试操作
                LazyCureContextHolder.push(0);
                return perfect.saveSoftSqlFile(nameDatabase);
            } catch (IOException | ProcessException | MethodParamFunctionException | ExecutionException | SQLException |
                     InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        Future<File> submit = LAZY_SMART_EXECUTOR.submit(() -> {
            try {
                if (peek != null) {
                    DynamicLazyDSContextHolder.push(peek);
                }     // 不做重试操作
                LazyCureContextHolder.push(0);
                return perfect.saveUpsertSqlFile(nameDatabase);
            } catch (IOException | ProcessException | MethodParamFunctionException | ExecutionException | SQLException |
                     InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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
        LAZY_SMART_EXECUTOR.execute(() -> autoStuffedLazyOperation.stuffed(schema, table, num));
    }

    @Override
    public void stuffed(Class table, Long num) {
        LAZY_SMART_EXECUTOR.execute(() -> autoStuffedLazyOperation.stuffed(table, num));
    }

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
    @Override
    public void stuffed(LazyTableInfo table, Long num) {
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        LAZY_SMART_EXECUTOR.execute(() -> {
            DynamicLazyDSContextHolder.push(peek);
            autoStuffedLazyOperation.stuffed(table, num);
        });
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
                LAZY_SMART_EXECUTOR.execute(() -> autoStuffedLazyOperation.stuffed(showTable, num));
            }
        }
    }

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
    public void stuffedJava(String schema, String tableName) {
        autoStuffedLazyOperation.stuffedJava(schema, tableName);
    }

    /**
     * 查询所有的数据库
     *
     * @return
     */
    @Override
    public List<LazyDatabase> showDatabases() {
        return perfect.showDatabases();
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

    /**
     * 查询数据库中的表的数据
     *
     * @param tableName 表名称
     * @param consumer  返回的数据
     * @return void
     */
    @Override
    public void scrollTableData(String tableName, Consumer<LazyPage<EasyHashMap>> consumer) {
        lazyLambdaStream.scroll(null, EasyHashMap.class, "select * from %s", consumer, LazyTableFieldUtil.cleanSpecialColumn(tableName));
    }

    /**
     * 查询数据库中的表的数据
     *
     * @param tableName 表名称
     * @param consumer  返回的数据
     * @return void
     */
    @Override
    public void scrollTableData(String tableName, LazyPage
            lazyPage, Consumer<LazyPage<EasyHashMap>> consumer) {
        lazyLambdaStream.scroll(lazyPage, EasyHashMap.class, "select * from %s", consumer, LazyTableFieldUtil.cleanSpecialColumn(tableName));
    }
}
