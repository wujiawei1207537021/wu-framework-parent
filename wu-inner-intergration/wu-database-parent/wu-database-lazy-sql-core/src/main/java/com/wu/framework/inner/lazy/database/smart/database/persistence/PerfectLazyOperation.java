package com.wu.framework.inner.lazy.database.smart.database.persistence;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.data.schema.SchemaMap;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.config.ExportDataConfiguration;
import com.wu.framework.inner.lazy.database.expand.database.persistence.config.LazyOperationProxyBeanAutoConfiguration;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.Perfect;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * description 懒人完美数据库持久层操作合集
 *
 * @author Jia wei Wu
 * @date 2021/2/22 下午7:56
 * @see LazyOperationProxyBeanAutoConfiguration
 */
@Slf4j
public class PerfectLazyOperation implements Perfect {


    /**
     * 表数据模版
     */
    private final String tableTemp =
            "\n" +
                    "-- ----------------------------\n" +
                    "-- LazyTableInfo data for %s   第【 %s ~ %s 】条数据  \n" +
                    "-- LazyTableInfo table_comment for %s \n" +
                    "-- ----------------------------";


    private final ExportDataConfiguration exportDataConfiguration;


    private final LazyLambdaStream lazyLambdaStream;

    public PerfectLazyOperation(ExportDataConfiguration exportDataConfiguration, LazyLambdaStream lazyLambdaStream) {
        this.exportDataConfiguration = exportDataConfiguration;
        this.lazyLambdaStream = lazyLambdaStream;
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
    public File saveSqlFile(String nameDatabase) throws IOException {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyLambdaStream.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        List<LazyTableInfo> lazyTableInfoList = lazyLambdaStream.selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                .eq(LazyTableInfo::getTableSchema, nameDatabase)
                .functionAs(String.format("concat('%s.',table_name)", nameDatabase), LazyTableInfo::getTableName)
        );

        // 数据库数据
        File file = FileUtil.createFile(System.getProperty("user.dir"), String.format("数据库%s数据.sql", nameDatabase));
        BufferedWriter fileBufferedWriter = FileUtil.createFileBufferedWriter(file);

        // -- use information_schema;
        fileBufferedWriter.newLine();
        fileBufferedWriter.write(String.format("use %s;", nameDatabase));
        fileBufferedWriter.newLine();

        for (LazyTableInfo table : lazyTableInfoList) {

            String countSQL = "select count(1) from {0} ";
            String tableName = table.getTableName();
            // 创建建表语句  show create table lazy.english_word;

            final SchemaMap createTableSchema = lazyLambdaStream.executeSQLForBean("show create table {0}", SchemaMap.class, tableName);
            final Object createTableSql = createTableSchema.get("Create Table");
//            //数据文件
//            BufferedWriter file = FileUtil.createFileBufferedWriter(filePackage ,
//                    String.format("表%s数据.sql", tableName));

            fileBufferedWriter.write("DROP TABLE IF EXISTS " + tableName.replace(nameDatabase + NormalUsedString.DOT, "") + NormalUsedString.SEMICOLON);
            fileBufferedWriter.newLine();
            fileBufferedWriter.write(String.valueOf(createTableSql));
            fileBufferedWriter.write(NormalUsedString.SEMICOLON);

            if (ObjectUtils.isEmpty(tableName)) {
                System.out.println(tableName);
            }
            Object tableComment = table.getTableComment();
            Long count = lazyLambdaStream.executeSQLForBean(countSQL, Long.class, tableName);
            if (count != 0) {
                AtomicReference<EasyHashMap> tableInfo = new AtomicReference<>();
                String selectSQL = "select * from {0} ";
                if (count > 1000) {

                    lazyLambdaStream.scroll(null, EasyHashMap.class, selectSQL, scrollPage -> {
                        try {
                            if (ObjectUtils.isEmpty(scrollPage.getRecord())) {
                                return;
                            }
                            List<EasyHashMap> record = (List<EasyHashMap>) scrollPage.getRecord();
                            // 忽略指定字段
                            final List<String> ignoreExportedFields = exportDataConfiguration.getIgnoreExportedFields();
                            if (!ObjectUtils.isEmpty(ignoreExportedFields)) {
                                EasyHashMap<Object, Object> one = record.get(0);
                                List<Object> ignoreFields = one.keySet().stream().filter(ignoreExportedFields::contains).collect(Collectors.toList());

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
                            fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                            fileBufferedWriter.newLine();
                            tableInfo.get().setUniqueLabel(tableName);
                            String s = LazyTableUpsertConverterFactory.upsert(record);
                            s = s.replaceAll("'true'", NormalUsedString.ONE).
                                    replaceAll("'false'", NormalUsedString.ZERO).
                                    replaceAll("'null'", NormalUsedString.NULL);
                            fileBufferedWriter.write(s);
                            fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                            fileBufferedWriter.newLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, tableName);


                } else {
                    List<EasyHashMap> tableDateList = lazyLambdaStream.executeSQL(selectSQL, EasyHashMap.class, tableName);


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

                        fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                        fileBufferedWriter.newLine();
                        tableInfo.set(tableDateList.get(0));
                        tableInfo.get().setUniqueLabel(tableName);
                        String s = LazyTableUpsertConverterFactory.upsert(tableDateList);
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        fileBufferedWriter.write(s);
                        fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                        fileBufferedWriter.newLine();
                    }

                }

            }

        }
        fileBufferedWriter.close();
        log.info("数据备份结束输出文件地址:" + System.getProperty("user.dir"));

        return file;
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
     * description 柔和形 数据库数据存储到sql文件(表存在不删除 数据使用upsert)
     *
     * @author Jia wei Wu
     * @date 2021/3/8 下午5:37
     */
    public void saveSoftSqlFile() throws ProcessException, SQLException, MethodParamFunctionException, IOException, ExecutionException, InterruptedException {
        final List<LazyDatabase> databaseList = showDatabases();
        for (LazyDatabase database : databaseList) {
            saveSoftSqlFile(database.getDatabase());
        }
    }

    /**
     * describe  柔和形 数据库数据存储到sql文件(表存在不删除 数据使用upsert)
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jia wei Wu
     * @date 2022/4/9 22:57
     **/
    @Override
    public File saveSoftSqlFile(String nameDatabase) throws ProcessException, SQLException, MethodParamFunctionException, IOException, ExecutionException, InterruptedException {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyLambdaStream.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyLambdaStream.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);


        // 数据库数据
        File file = FileUtil.createFile(System.getProperty("user.dir"), String.format("数据库%s数据.sql", nameDatabase));
        BufferedWriter fileBufferedWriter = FileUtil.createFileBufferedWriter(file);

        // -- use information_schema;
        fileBufferedWriter.newLine();
        fileBufferedWriter.write(String.format("use %s;", nameDatabase));
        fileBufferedWriter.newLine();

        for (EasyHashMap table : allTables) {

            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            // 创建建表语句  show create table lazy.english_word;

            SchemaMap createTableSchema = lazyLambdaStream.executeSQLForBean("show create table %s", SchemaMap.class, tableName);
            String createTableSql = String.valueOf(createTableSchema.get("Create Table"));
//         CREATE TABLE `table`  ====>>>>>>>   CREATE TABLE IF NOT EXISTS `table`
            fileBufferedWriter.newLine();
            createTableSql = createTableSql.replaceAll("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
            fileBufferedWriter.write(createTableSql);
            fileBufferedWriter.write(NormalUsedString.SEMICOLON);

            Object tableComment = table.get("TABLECOMMENT");
            Long count = lazyLambdaStream.executeSQLForBean(String.format(countSQL, tableName), Long.class);
            if (count != 0) {
                AtomicReference<EasyHashMap> tableInfo = new AtomicReference<>();
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    lazyLambdaStream.scroll(null, EasyHashMap.class, selectSQL, scrollPage -> {
                        try {
                            if (ObjectUtils.isEmpty(scrollPage.getRecord())) {
                                return;
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
                            fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                            fileBufferedWriter.newLine();
                            tableInfo.get().setUniqueLabel(tableName);
                            String s = LazyTableUpsertConverterFactory.upsert(record);
                            s = s.replaceAll("'true'", NormalUsedString.ONE).
                                    replaceAll("'false'", NormalUsedString.ZERO).
                                    replaceAll("'null'", NormalUsedString.NULL);
                            fileBufferedWriter.write(s);
                            fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                            fileBufferedWriter.newLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, tableName);


                } else {
                    List<EasyHashMap> tableDateList = lazyLambdaStream.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);


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

                        fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                        fileBufferedWriter.newLine();
                        tableInfo.set(tableDateList.get(0));
                        tableInfo.get().setUniqueLabel(tableName);
                        String s = LazyTableUpsertConverterFactory.upsert(tableDateList);
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        fileBufferedWriter.write(s);
                        fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                        fileBufferedWriter.newLine();
                    }

                }

            }

        }
        fileBufferedWriter.close();
        log.info("数据备份结束输出文件地址:" + System.getProperty("user.dir"));
        return file;

    }

    /**
     * describe  柔和形 数据库表结构
     *
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据库表结构到本地数据
     * @author Jia wei Wu
     * @date 2022/4/9 22:57
     **/
    @Override
    public String saveSoftTableStructureSql(String nameDatabase) {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyLambdaStream.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyLambdaStream.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);


        // 数据库数据

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(NormalUsedString.NEWLINE);
        // -- use information_schema;
        stringBuilder.append(String.format("use %s;", nameDatabase));


        for (EasyHashMap table : allTables) {

            String tableName = table.get("tableName").toString();
            // 创建建表语句  show create table lazy.english_word;

            SchemaMap createTableSchema = lazyLambdaStream.executeSQLForBean("show create table %s", SchemaMap.class, tableName);
            String createTableSql = String.valueOf(createTableSchema.get("Create Table"));
//         CREATE TABLE `table`  ====>>>>>>>   CREATE TABLE IF NOT EXISTS `table`
            stringBuilder.append(NormalUsedString.NEWLINE);
            createTableSql = createTableSql.replaceAll("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
            stringBuilder.append(createTableSql);
            stringBuilder.append(NormalUsedString.SEMICOLON);
            stringBuilder.append(NormalUsedString.NEWLINE);
        }
        return stringBuilder.toString();
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
    public File saveUpsertSqlFile(String nameDatabase) throws ProcessException, SQLException, MethodParamFunctionException, IOException, ExecutionException, InterruptedException {
        // 文件前缀
        String filePackage = System.getProperty("user.dir") + File.separator + "data_file" + File.separator + nameDatabase;

        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyLambdaStream.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyLambdaStream.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);


        // 数据库数据
        // 数据库数据
        File file = FileUtil.createFile(System.getProperty("user.dir"), String.format("数据库%s数据.sql", nameDatabase));
        BufferedWriter fileBufferedWriter = FileUtil.createFileBufferedWriter(file);

        // -- use information_schema;
        fileBufferedWriter.newLine();
        fileBufferedWriter.write(String.format("use %s;", nameDatabase));
        fileBufferedWriter.newLine();

        for (EasyHashMap table : allTables) {

            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            if (ObjectUtils.isEmpty(tableName)) {
                continue;
            }

            Object tableComment = table.get("TABLECOMMENT");
            Long count = lazyLambdaStream.executeSQLForBean(String.format(countSQL, tableName), Long.class);
            if (count != 0) {
                AtomicReference<EasyHashMap> tableInfo = new AtomicReference<>();
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    lazyLambdaStream.scroll(null, EasyHashMap.class, String.format(selectSQL, tableName), scrollPage -> {
                        try {
                            if (ObjectUtils.isEmpty(scrollPage.getRecord())) {
                                return;
                            }
                            List<EasyHashMap> record = (List<EasyHashMap>) scrollPage.getRecord();
                            // 忽略指定字段
                            final List<String> ignoreExportedFields = exportDataConfiguration.getIgnoreExportedFields();
                            if (!ObjectUtils.isEmpty(ignoreExportedFields)) {
                                EasyHashMap<Object, Object> one = record.get(0);
                                List<Object> ignoreFields = one.keySet().stream().filter(ignoreExportedFields::contains).collect(Collectors.toList());

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
                            fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                            fileBufferedWriter.newLine();
                            tableInfo.get().setUniqueLabel(tableName);
                            String s = LazyTableUpsertConverterFactory.upsert(record);
                            s = s.replaceAll("'true'", NormalUsedString.ONE).
                                    replaceAll("'false'", NormalUsedString.ZERO).
                                    replaceAll("'null'", NormalUsedString.NULL);
                            fileBufferedWriter.write(s);
                            fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                            fileBufferedWriter.newLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });


                } else {
                    List<EasyHashMap> tableDateList = lazyLambdaStream.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);


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

                        fileBufferedWriter.write(String.format(tableTemp, tableName, from, to, tableComment));
                        fileBufferedWriter.newLine();
                        tableInfo.set(tableDateList.get(0));
                        tableInfo.get().setUniqueLabel(tableName);
                        String s = LazyTableUpsertConverterFactory.upsert(tableDateList);
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        fileBufferedWriter.write(s);
                        fileBufferedWriter.write(NormalUsedString.SEMICOLON);
                        fileBufferedWriter.newLine();
                    }

                }

            }

        }
        fileBufferedWriter.close();
        log.info("数据备份结束输出文件地址:" + System.getProperty("user.dir"));
        return file;
    }

    /**
     * 查询所有数据库名称
     */
    @Override
    public List<LazyDatabase> showDatabases() {
        List<LazyDatabase> databaseList = lazyLambdaStream.executeSQL("show databases;", LazyDatabase.class);
        return databaseList;
    }

    /**
     * 查询所有的表
     *
     * @return
     */
    @Override
    public List<LazyTableInfo> showTables(@NonNull String schemaName) {
//        final Object table_schema = lambdaStream.select().table(LazyTableInfo.class).eq("table_schema", schemaName).collection();
//        String sqlSelectTable = "select * from information_schema.tables where table_schema = '%s' ";
//        final List<LazyTableInfo> tables = lazyLambdaStream.executeSQL(sqlSelectTable, LazyTableInfo.class, schemaName);
        List<LazyTableInfo> tables = lazyLambdaStream.selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                .eq(LazyTableInfo::getTableSchema, schemaName));
        return tables;
    }

}
