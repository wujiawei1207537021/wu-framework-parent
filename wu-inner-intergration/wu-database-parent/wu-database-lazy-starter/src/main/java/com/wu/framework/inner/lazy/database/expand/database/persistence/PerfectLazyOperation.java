package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.map.EasyHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * description 懒人完美数据库持久层操作合集
 *
 * @author Jia wei Wu
 * @date 2021/2/22 下午7:56
 */
@Slf4j
public class PerfectLazyOperation {

    private final LazyBaseOperation lazyBaseOperation;

    private final MySQLDataProcessAnalyze mySQLDataProcessAnalyze = new MySQLDataProcessAnalyze() {
    };

    public PerfectLazyOperation(LazyBaseOperation lazyBaseOperation) {
        this.lazyBaseOperation = lazyBaseOperation;
    }

    /**
     * description 滚动查询
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/8 下午4:33
     */
    public <T> void scroll(Page page, @NonNull Class<T> returnType, String sql,
                           MethodParamFunction<Page<T>> methodParamFunction,
                           Object... params) throws ExecutionException, InterruptedException, MethodParamFunctionException, IOException, ProcessException, SQLException {
        if (ObjectUtils.isEmpty(page)) {
            page = new Page<>(1, 1000);
        }
        ExecutorService executorService = null;

        do {
            if (page.getPages() > 10) {
                if (null == executorService) {
                    executorService = Executors.newWorkStealingPool(10);
                }
                page.setCurrent(page.getCurrent() + 1);
                Page finalPage = page;
                executorService.submit(() -> {
                    try {
                        methodParamFunction.defaultMethod(lazyBaseOperation.page(finalPage, returnType, sql, params));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).get();
            } else {
                Page<T> pageResult = lazyBaseOperation.page(page, returnType, sql, params);
                methodParamFunction.defaultMethod(pageResult);
                log.info("当前查询页数:" + page.getCurrent());
                page.setCurrent(page.getCurrent() + 1);
            }
        } while (page.getRecord() != null && page.getRecord().size() == page.getSize());
    }


    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @description 数据库数据存储到sql文件
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    public void saveSqlFile(String nameDatabase) throws IOException, ProcessException, MethodParamFunctionException, ExecutionException, InterruptedException, SQLException {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyBaseOperation.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyBaseOperation.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);
        BufferedWriter file = FileUtil.createFile(System.getProperty("user.dir"), String.format("数据库%s数据.sql", nameDatabase));
        for (EasyHashMap table : allTables) {
            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            Integer count = lazyBaseOperation.executeSQLForBean(String.format(countSQL, tableName), Integer.class);
            if (count != 0) {
                AtomicReference<EasyHashMap> tableInfo = new AtomicReference<>();
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    Page page = new Page<>(1, 1000);
                    scroll(page, EasyHashMap.class, selectSQL, scrollList -> {
                        if (ObjectUtils.isEmpty(scrollList.getRecord())) {
                            return scrollList;
                        }
                        List<EasyHashMap> record = (List<EasyHashMap>) scrollList.getRecord();
                        tableInfo.set(record.get(0));
                        file.write("-- " + tableName);
                        file.newLine();
                        tableInfo.get().setUniqueLabel(tableName);
                        MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = mySQLDataProcessAnalyze.upsertDataPack(record, tableInfo.get().toEasyTableAnnotation(false));
                        String s = mySQLProcessResult.getSql();
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        file.write(s);
                        file.write(NormalUsedString.SEMICOLON);
                        file.newLine();
                        page.setCurrent(page.getCurrent() + 1);
                        return scrollList;
                    }, tableName);


                } else {
                    List<EasyHashMap> tableDateList = lazyBaseOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);
                    if (!ObjectUtils.isEmpty(tableDateList)) {
                        file.write("-- " + tableName);
                        file.newLine();
                        tableInfo.set(tableDateList.get(0));
                        tableInfo.get().setUniqueLabel(tableName);
                        MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = mySQLDataProcessAnalyze.upsertDataPack(tableDateList, tableInfo.get().toEasyTableAnnotation(false));
                        String s = mySQLProcessResult.getSql();
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        file.write(s);
                        file.write(NormalUsedString.SEMICOLON);
                        file.newLine();
                    }

                }

            }
        }
        System.out.println("数据备份结束输出文件地址:" + System.getProperty("user.dir"));
        file.close();
    }

    /**
     * description Mysql 服务器迁移本地文件sql
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/3/8 下午5:37
     */
    public void saveSqlFile() throws ProcessException, SQLException, MethodParamFunctionException, IOException, ExecutionException, InterruptedException {
        List<EasyHashMap> easyHashMaps = lazyBaseOperation.executeSQL("show databases;", EasyHashMap.class);
        for (EasyHashMap easyHashMap : easyHashMaps) {
            saveSqlFile(easyHashMap.get("Database").toString());
        }
    }

}
