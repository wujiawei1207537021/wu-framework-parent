package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.data.schema.SchemaMap;
import com.wu.framework.inner.layer.stereotype.MethodParamFunction;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.config.ExportDataConfiguration;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.persistence.analyze.DefaultMySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.analyze.MySQLDataProcessAnalyze;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * description 懒人完美数据库持久层操作合集
 *
 * @author Jia wei Wu
 * @date 2021/2/22 下午7:56
 */
@Slf4j
public class PerfectLazyOperation implements Perfect {


    /*
 Easy Upsert Data Transfer

 Source Server         : ETMS2.0
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : 192.168.17.221:30502
 Source Schema         : etms_2_0_central_user

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 06/08/2021 15:56:31
*/

    /**
     * 表数据模版
     */
    private final String tableTemp =
            "\n" +
                    "-- ----------------------------\n" +
                    "-- LazyTableInfo data for %s   第【 %s ~ %s 】条数据  \n" +
                    "-- LazyTableInfo table_comment for %s \n" +
                    "-- ----------------------------";

    private final LazyBaseOperation lazyBaseOperation;
    private final ExportDataConfiguration exportDataConfiguration;

    private final DefaultMySQLDataProcessAnalyze mySQLDataProcessAnalyze = new DefaultMySQLDataProcessAnalyze();


    public PerfectLazyOperation(LazyBaseOperation lazyBaseOperation, ExportDataConfiguration exportDataConfiguration) {
        this.lazyBaseOperation = lazyBaseOperation;
        this.exportDataConfiguration = exportDataConfiguration;
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
     * description 数据库数据存储到sql文件
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    @Override
    public void saveSqlFile(String nameDatabase) throws IOException, ProcessException, MethodParamFunctionException, ExecutionException, InterruptedException, SQLException {
        // 文件前缀
        String filePackage = System.getProperty("user.dir") + File.separator + "data_file" + File.separator + nameDatabase;

        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyBaseOperation.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyBaseOperation.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);


        // 数据库数据
        BufferedWriter file = FileUtil.createFile(System.getProperty("user.dir"), String.format("数据库%s数据.sql", nameDatabase));

        // -- use information_schema;
        file.newLine();
        file.write(String.format("use %s;", nameDatabase));
        file.newLine();

        for (EasyHashMap table : allTables) {

            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            // 创建建表语句  show create table lazy.english_word;

            final SchemaMap createTableSchema = lazyBaseOperation.executeSQLForBean("show create table %s", SchemaMap.class, tableName);
            final Object createTableSql = createTableSchema.get("Create Table");
//            //数据文件
//            BufferedWriter file = FileUtil.createFile(filePackage ,
//                    String.format("表%s数据.sql", tableName));

            file.write("DROP TABLE IF EXISTS " + tableName.replace(nameDatabase + NormalUsedString.DOT, "") + NormalUsedString.SEMICOLON);
            file.newLine();
            file.write(String.valueOf(createTableSql));
            file.write(NormalUsedString.SEMICOLON);

            Object tableComment = table.get("TABLECOMMENT");
            Long count = lazyBaseOperation.executeSQLForBean(String.format(countSQL, tableName), Long.class);
            if (count != 0) {
                AtomicReference<EasyHashMap> tableInfo = new AtomicReference<>();
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    Page page = new Page<>(1, 1000);
                    scroll(page, EasyHashMap.class, selectSQL, scrollPage -> {
                        if (ObjectUtils.isEmpty(scrollPage.getRecord())) {
                            return scrollPage;
                        }
                        List<EasyHashMap> record = (List<EasyHashMap>) scrollPage.getRecord();
                        // 忽略指定字段
                        final List<String> ignoreExportedFields = exportDataConfiguration.getIgnoreExportedFields();
                        if (!ObjectUtils.isEmpty(ignoreExportedFields)) {
                            EasyHashMap<Object, Object> one = record.get(0);
                            List<Object> ignoreFields = one.keySet().stream().filter(key -> ignoreExportedFields.contains(key)).collect(Collectors.toList());

                            if (!ObjectUtils.isEmpty(ignoreFields)) {
                                // 忽略后的数据
                                List<EasyHashMap> ignoredData = record.stream().map(easyHashMap -> {
                                    for (Object ignoreField : ignoreFields) {
                                        easyHashMap.remove(ignoreField);
                                    }
                                    return easyHashMap;
                                }).collect(Collectors.toList());
                                record = ignoredData;
                            }

                        }

                        tableInfo.set(record.get(0));
                        int from = (scrollPage.getCurrent() - 1) * scrollPage.getSize();
                        int to = from + scrollPage.getRecord().size();
                        file.write(String.format(tableTemp, tableName, from, to, tableComment));
                        file.newLine();
                        tableInfo.get().setUniqueLabel(tableName);
                        MySQLDataProcessAnalyze.MySQLProcessResult mySQLProcessResult = mySQLDataProcessAnalyze.upsertDataPack(record,
                                tableInfo.get().toEasyTableAnnotation(false, true));
                        String s = mySQLProcessResult.getSql();
                        s = s.replaceAll("'true'", NormalUsedString.ONE).
                                replaceAll("'false'", NormalUsedString.ZERO).
                                replaceAll("'null'", NormalUsedString.NULL);
                        file.write(s);
                        file.write(NormalUsedString.SEMICOLON);
                        file.newLine();
                        page.setCurrent(page.getCurrent() + 1);
                        return scrollPage;
                    }, tableName);


                } else {
                    List<EasyHashMap> tableDateList = lazyBaseOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);


                    if (!ObjectUtils.isEmpty(tableDateList)) {
                        // 忽略指定字段
                        final List<String> ignoreExportedFields = exportDataConfiguration.getIgnoreExportedFields();
                        if (!ObjectUtils.isEmpty(ignoreExportedFields)) {
                            EasyHashMap<Object, Object> one = tableDateList.get(0);
                            List<Object> ignoreFields = one.keySet().stream().filter(key -> ignoreExportedFields.contains(key)).collect(Collectors.toList());

                            if (!ObjectUtils.isEmpty(ignoreFields)) {
                                // 忽略后的数据
                                List<EasyHashMap> ignoredData = tableDateList.stream().map(easyHashMap -> {
                                    for (Object ignoreField : ignoreFields) {
                                        easyHashMap.remove(ignoreField);
                                    }
                                    return easyHashMap;
                                }).collect(Collectors.toList());
                                tableDateList = ignoredData;
                            }

                        }
                        int from = 0;
                        int to = tableDateList.size();

                        file.write(String.format(tableTemp, tableName, from, to, tableComment));
                        file.newLine();
                        tableInfo.set(tableDateList.get(0));
                        tableInfo.get().setUniqueLabel(tableName);
                        MySQLDataProcessAnalyze.MySQLProcessResult
                                mySQLProcessResult = mySQLDataProcessAnalyze.upsertDataPack(tableDateList,
                                tableInfo.get().toEasyTableAnnotation(false, true));
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
        file.close();
        log.info("数据备份结束输出文件地址:" + System.getProperty("user.dir"));


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
        final List<LazyDatabase> databaseList = showDatabases();
        for (LazyDatabase database : databaseList) {
            saveSqlFile(database.getDatabase());
        }
    }


    /**
     * 查询所有数据库名称
     */
    @Override
    public List<LazyDatabase> showDatabases() {
        List<LazyDatabase> databaseList = lazyBaseOperation.executeSQL("show databases;", LazyDatabase.class);
        return databaseList;
    }

    /**
     * 查询所有的表
     *
     * @return
     */
    @Override
    public List<LazyTableInfo> showTables(@NonNull String tableSchema) {
//        final Object table_schema = lambdaStream.select().table(LazyTableInfo.class).eq("table_schema", tableSchema).collection();
        String sqlSelectTable = "select * from information_schema.tables where table_schema = '%s' ";
        final List<LazyTableInfo> tables = lazyBaseOperation.executeSQL(sqlSelectTable, LazyTableInfo.class, tableSchema);
        return tables;
    }

}
