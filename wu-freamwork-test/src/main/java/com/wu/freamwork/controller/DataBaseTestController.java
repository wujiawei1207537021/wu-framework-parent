package com.wu.freamwork.controller;


import com.sun.media.sound.JavaSoundAudioClip;
import com.wu.framework.easy.stereotype.dynamic.toolkit.DynamicEasyUpsertDSContextHolder;
import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import com.wu.framework.inner.dynamic.database.component.CDS;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 数据库测试
 * @date : 2020/6/27 下午7:15
 */
//@EasyController
public class DataBaseTestController implements CommandLineRunner {

    private final LazyOperation lazyOperation;
    private final UpsertConfig upsertConfig;
    private final MySQLDataProcess mySQLDataProcess;
    private final IUpsert iUpsert;

    public DataBaseTestController(LazyOperation lazyOperation, UpsertConfig upsertConfig, MySQLDataProcess mySQLDataProcess, IUpsert iUpsert) {
        this.lazyOperation = lazyOperation;
        this.upsertConfig = upsertConfig;
        this.mySQLDataProcess = mySQLDataProcess;
        this.iUpsert = iUpsert;
    }

    public static void main(String[] args) throws Exception {

        JavaSoundAudioClip javaSoundAudioClip = new JavaSoundAudioClip(new FileInputStream(new File("/Users/wujiawei/Desktop/aa.mp3")));
//        AudioClip audioClip = Applet.newAudioClip(new URL("/Users/wujiawei/Music/QQ音/任贤齐,张柏芝-星语心愿.mp3"));
//        audioClip.play();
        javaSoundAudioClip.play();
    }

    //    @CDS("localhost")
    @Override
    public void run(String... args) throws Exception {
//        upsert();
//        insert();
//        update();
//        delete();
//        select();
//        System.out.println(iUserDao.findAll());
//        SQLConverter.createSelectSQL(OmTpsmPubOthEqpOpemngVehicleRegistration.class);
//        List<String> ss = lazyOperation.executeSQL("select id from user", String.class);
//        System.out.println(ss);
//        lazyOperation.activeUpsert(new DataBaseUser().setAge(20));
//        hc();
//        Page<DataBaseUser> dataBaseUserPage = new Page<DataBaseUser>(1, 1000);
//        Page<DataBaseUser> page = lazyOperation.page(dataBaseUserPage, DataBaseUser.class, null);
//        System.out.println(page);
        mysqlServerMigration();
        // 数据迁移
//        dataMigration(null);
//        dataMigration("test", "upsert");
//        final List<EasyHashMap> upsertBinary = lazyOperation.executeSQL("SELECT * FROM upsert_binary limit 1", EasyHashMap.class);
//        System.out.println(upsertBinary);
    }

    /**
     * 更新或者插入
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午1:55
     **/
//    @Scheduled(fixedRate = 1000)
    public void upsert() {
        long s = System.currentTimeMillis();
        List<DataBaseUser> dataBaseUserList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setAddress("address");
            dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
            dataBaseUser.setSex("woman");
            dataBaseUser.setUsername("methodName" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        lazyOperation.upsertList(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("共计用时：" + (e - s) + "ms");
    }

    /**
     * 插入数据
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午1:56
     **/
    public void insert() {
        long s = System.currentTimeMillis();
        List<DataBaseUser> dataBaseUserList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setAddress("address");
            dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
            dataBaseUser.setSex("woman");
            dataBaseUser.setUsername("methodName" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        lazyOperation.insertList(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("共计用时：" + (e - s) + "ms");
    }

    /**
     * 更新数据
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午1:56
     **/
    public void update() {
        long s = System.currentTimeMillis();
        List<DataBaseUser> dataBaseUserList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setAddress("address");
            dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
            dataBaseUser.setSex("woman");
            dataBaseUser.setUsername("methodName" + i);
            dataBaseUserList.add(dataBaseUser);
            lazyOperation.updateById(dataBaseUser);
        }
        lazyOperation.updateAllByIdList(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("共计用时：" + (e - s) + "ms");
    }

    /**
     * 删除
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午5:05
     **/
    public void delete() {
        long s = System.currentTimeMillis();
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setAddress("address");
        dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
        dataBaseUser.setSex("woman");
        dataBaseUser.setUsername("methodName");
        lazyOperation.deleteById(dataBaseUser);
        long e = System.currentTimeMillis();
        System.out.println("共计用时：" + (e - s) + "ms");
    }

    /**
     * 查询
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午5:05
     **/
    public void select() {
        long s = System.currentTimeMillis();
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setAddress("address");
//        dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
        dataBaseUser.setSex("woman");
        dataBaseUser.setUsername("methodName");
        DataBaseUser dataBaseUser1 = lazyOperation.selectOne(dataBaseUser);
        System.out.println(dataBaseUser1);

        dataBaseUser.setUsername(null);
        List<DataBaseUser> dataBaseUserList = lazyOperation.selectAll(dataBaseUser);
        System.out.println(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("共计用时：" + (e - s) + "ms");
    }

    /**
     * description hc系统升级数据同步
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2021/1/21 下午7:31
     */
    public void hc() throws Exception {
        // 获取数据库中所有的表
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) ";
        List<EasyHashMap> allTables = lazyOperation.executeSQL(sql, EasyHashMap.class);

        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), "hc6.3.3升级sql脚本.sql");
        for (EasyHashMap table : allTables) {
            String countSQL = "select count(1) from %s ";
            String tableName = table.get("TABLE_NAME").toString();
            Integer count = lazyOperation.executeSQLForBean(String.format(countSQL, tableName.toString()), Integer.class);
            if (count != 0) {
                String selectSQL = "select * from %s ";
                List<EasyHashMap> tableDateList = lazyOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);


                // ho_dictionary_data
                if (tableName.equals("ho_dictionary_data")) {
                    for (EasyHashMap easyHashMap : tableDateList) {
                        Object code = easyHashMap.get("code");
                        Object dictionaryCode = easyHashMap.get("dictionary_code");
                        Object id = easyHashMap.get("id");
                        easyHashMap.put("id", String.format("wujiaweiifnull((select T1.id from %s T1 where T1.code='%s' and T1.dictionary_code='%s'),%s)wujiawei", tableName, code, dictionaryCode, id));
                    }
                }

                // 字典
                // 用户操作表
                if (tableName.equals("ho_operate") || tableName.equals("ho_dictionary")) {
                    for (EasyHashMap easyHashMap : tableDateList) {
                        Object code = easyHashMap.get("code");
                        Object id = easyHashMap.get("id");
                        easyHashMap.put("id", String.format("wujiaweiifnull((select T1.id from %s T1 where T1.code='%s'),%s)wujiawei", tableName, code, id));
                    }
                }
                System.out.println(tableDateList);
                file.write("-- " + tableName);
                file.newLine();
                EasyHashMap tableInfo = tableDateList.get(0);
                tableInfo.setUniqueLabel(tableName);
                final MySQLDataProcess.MySQLProcessResult mySQLProcessResult = mySQLDataProcess.dataPack(tableDateList, tableInfo.toEasyTableAnnotation(false));
                String s = mySQLProcessResult.getSql();
                s = s.replaceAll("'true'", "1").
                        replaceAll("'false'", "0").
                        replaceAll("'null'", "null").
                        replaceAll("'wujiawei", "").
                        replaceAll("wujiawei'", "");

                file.write(s);
                file.write(";");
                file.newLine();
            }
        }
        file.close();

        // 获取表中所有的数据
        // 将数据转换为更新语句
    }

    /**
     * description Mysql 服务器迁移
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/3/8 下午5:37
     */
    public void mysqlServerMigration() throws Exception {
        List<EasyHashMap> easyHashMaps = lazyOperation.executeSQL("show databases;", EasyHashMap.class);
        for (EasyHashMap easyHashMap : easyHashMaps) {
            dataMigration(easyHashMap.get("Database").toString());
        }
    }

    /**
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return 保存数据到本地数据
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    public void dataMigration(String nameDatabase) throws Exception {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = lazyOperation.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyOperation.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);
        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), String.format("数据库%s数据.sql", nameDatabase));
        for (EasyHashMap table : allTables) {
            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            Integer count = lazyOperation.executeSQLForBean(String.format(countSQL, tableName), Integer.class);
            if (count != 0) {
                EasyHashMap tableInfo;
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    Page page = new Page<>(1, 1000);
                    do {
                        lazyOperation.page(page, EasyHashMap.class, selectSQL, tableName);
                        final List<EasyHashMap> record = (List<EasyHashMap>) page.getRecord();
                        tableInfo = record.get(0);
                        file.write("-- " + tableName);
                        file.newLine();
                        tableInfo.setUniqueLabel(tableName);
                        final MySQLDataProcess.MySQLProcessResult mySQLProcessResult = mySQLDataProcess.dataPack(record, tableInfo.toEasyTableAnnotation(false));
                        String s = mySQLProcessResult.getSql();
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        file.write(s);
                        file.write(NormalUsedString.SEMICOLON);
                        file.newLine();
                        System.out.println("当前查询页数:" + page.getCurrent());
                        page.setCurrent(page.getCurrent() + 1);
                    } while (page.getRecord() != null && page.getRecord().size() == page.getSize());


                } else {
                    List<EasyHashMap> tableDateList = lazyOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);
                    file.write("-- " + tableName);
                    file.newLine();
                    tableInfo = tableDateList.get(0);
                    tableInfo.setUniqueLabel(tableName);
                    MySQLDataProcess.MySQLProcessResult mySQLProcessResult = mySQLDataProcess.dataPack(tableDateList, tableInfo.toEasyTableAnnotation(false));
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
        System.out.println("数据备份结束输出文件地址:" + upsertConfig.getCacheFileAddress());
        file.close();
    }

    /**
     * @param source 源数据库
     * @param target 目标
     * @description 多线程奔跑 🏃
     * @author Jia wei Wu
     * @date 2021/2/22 下午8:07
     */
    public void dataMigration(String source, String target) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 50, 20, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(20));
        // 当前数据库
        if (source == null) {
            source = lazyOperation.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = lazyOperation.executeSQL(String.format(sqlSelectTable, source, source), EasyHashMap.class);
        EasyUpsertDS easyUpsertDS = new EasyUpsertDS() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return EasyUpsertDS.class;
            }

            @Override
            public String value() {
                return target;
            }

            @Override
            public String name() {
                return target;
            }

            @Override
            public EasyUpsertType type() {
                return EasyUpsertType.MySQL;
            }
        };
        String finalSource = source;
        allTables.forEach(easyHashMap -> {
            threadPoolExecutor.execute(() -> {
                singleTableDataProcess(finalSource, easyHashMap, easyUpsertDS);
            });
        });

        System.out.println("数据备份结束输出文件地址:" + upsertConfig.getCacheFileAddress());
    }

    /**
     * description 单表数据处理
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/2/23 下午6:48
     */
    public void singleTableDataProcess(String source, EasyHashMap table, EasyUpsertDS easyUpsertDS) {
        String countSQL = "select count(1) from %s ";
        String tableName = table.get("tableName").toString();
        Integer count = lazyOperation.executeSQLForBean(String.format(countSQL, tableName), Integer.class);
        if (count != 0) {
            EasyHashMap tableInfo;
            String selectSQL = "select * from %s ";
            if (count > 1000) {
                Page page = new Page<>(1, 1000);
                do {
                    lazyOperation.page(page, EasyHashMap.class, selectSQL, tableName);
                    final List<EasyHashMap> record = (List<EasyHashMap>) page.getRecord();
                    tableInfo = record.get(0);
                    tableInfo.setUniqueLabel(tableName);
                    DynamicEasyUpsertDSContextHolder.push(easyUpsertDS);
                    iUpsert.upsert(record);
                    DynamicEasyUpsertDSContextHolder.clear();
                    System.out.println("当前查询页数:" + page.getCurrent());
                    page.setCurrent(page.getCurrent() + 1);
                } while (page.getRecord() != null && page.getRecord().size() == page.getSize());
            } else {
                List<EasyHashMap> tableDateList = lazyOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);
                tableInfo = tableDateList.get(0);
                tableInfo.setUniqueLabel(tableName.replace(source, easyUpsertDS.name()));
                DynamicEasyUpsertDSContextHolder.push(easyUpsertDS);
                iUpsert.upsert(tableDateList);
                DynamicEasyUpsertDSContextHolder.clear();
            }
        }
    }

}
