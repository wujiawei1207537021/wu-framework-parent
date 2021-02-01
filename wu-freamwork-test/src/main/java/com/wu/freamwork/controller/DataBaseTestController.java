package com.wu.freamwork.controller;


import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import org.springframework.boot.CommandLineRunner;

import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import com.wu.framework.inner.dynamic.database.component.CDS;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 数据库测试
 * @date : 2020/6/27 下午7:15
 */
@EasyController
public class DataBaseTestController implements CommandLineRunner {

    private final LazyOperation layerOperation;
    private final UpsertConfig upsertConfig;
    private final MySQLDataProcess mySQLDataProcess;

    public DataBaseTestController(LazyOperation layerOperation, UpsertConfig upsertConfig, MySQLDataProcess mySQLDataProcess) {
        this.layerOperation = layerOperation;
        this.upsertConfig = upsertConfig;
        this.mySQLDataProcess = mySQLDataProcess;
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
//        List<String> ss = layerOperation.executeSQL("select id from user", String.class);
//        System.out.println(ss);
//        layerOperation.activeUpsert(new DataBaseUser().setAge(20));
//        hc();
        Page<DataBaseUser> dataBaseUserPage = new Page<DataBaseUser>(1, 1000);
        Page<DataBaseUser> page = layerOperation.page(dataBaseUserPage, DataBaseUser.class, null);
        System.out.println(page);
        // 数据迁移
        dataMigration(null);
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
            dataBaseUser.setUsername("name" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        layerOperation.upsertList(dataBaseUserList);
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
            dataBaseUser.setUsername("name" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        layerOperation.insertList(dataBaseUserList);
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
            dataBaseUser.setUsername("name" + i);
            dataBaseUserList.add(dataBaseUser);
            layerOperation.updateById(dataBaseUser);
        }
        layerOperation.updateAllByIdList(dataBaseUserList);
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
        dataBaseUser.setUsername("name");
        layerOperation.deleteById(dataBaseUser);
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
        dataBaseUser.setUsername("name");
        DataBaseUser dataBaseUser1 = layerOperation.selectOne(dataBaseUser);
        System.out.println(dataBaseUser1);

        dataBaseUser.setUsername(null);
        List<DataBaseUser> dataBaseUserList = layerOperation.selectAll(dataBaseUser);
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
        List<EasyHashMap> allTables = layerOperation.executeSQL(sql, EasyHashMap.class);

        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), "hc6.3.3升级sql脚本.sql");
        for (EasyHashMap table : allTables) {
            String countSQL = "select count(1) from %s ";
            String tableName = table.get("TABLE_NAME").toString();
            Integer count = layerOperation.executeSQLForBean(String.format(countSQL, tableName.toString()), Integer.class);
            if (count != 0) {
                String selectSQL = "select * from %s ";
                List<EasyHashMap> tableDateList = layerOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);


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
                String s = mySQLDataProcess.dataPack(tableDateList, tableInfo.toEasyTableAnnotation(false));
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
     * @param nameDatabase 数据库名 默认当前连接数据
     *                     System.getProperty("user.dir") 数据文件地址
     * @return
     * @author Jiawei Wu
     * @date 2021/1/31 6:40 下午
     **/
    public void dataMigration(String nameDatabase) throws Exception {
        // 当前数据库
        if (nameDatabase == null) {
            nameDatabase = layerOperation.executeSQLForBean("select database()", String.class);
        }
        // 获取数据库中所有的表
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";
        List<EasyHashMap> allTables = layerOperation.executeSQL(String.format(sqlSelectTable, nameDatabase, nameDatabase), EasyHashMap.class);
        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), String.format("数据库%s数据.sql", nameDatabase));
        for (EasyHashMap table : allTables) {
            String countSQL = "select count(1) from %s ";
            String tableName = table.get("tableName").toString();
            Integer count = layerOperation.executeSQLForBean(String.format(countSQL, tableName), Integer.class);
            if (count != 0) {
                EasyHashMap tableInfo;
                String selectSQL = "select * from %s ";
                if (count > 1000) {

                    Page page = new Page<>(1, 1000);
                    do {
                        layerOperation.page(page, EasyHashMap.class, selectSQL, tableName);
                        final List<EasyHashMap> record = (List<EasyHashMap>) page.getRecord();
                        tableInfo = record.get(0);
                        file.write("-- " + tableName);
                        file.newLine();
                        tableInfo.setUniqueLabel(tableName);
                        String s = mySQLDataProcess.dataPack(record, tableInfo.toEasyTableAnnotation(false));
                        s = s.replaceAll("'true'", "1").
                                replaceAll("'false'", "0").
                                replaceAll("'null'", NormalUsedString.NULL);
                        file.write(s);
                        file.write(NormalUsedString.SEMICOLON);
                        file.newLine();
                        System.out.println("当前查询页数:"+page.getCurrent());
                        page.setCurrent(page.getCurrent()+1);
                    }while (page.getRecord()!=null&&page.getRecord().size()==page.getSize());


                } else {
                    List<EasyHashMap> tableDateList = layerOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);
                    file.write("-- " + tableName);
                    file.newLine();
                    tableInfo = tableDateList.get(0);
                    tableInfo.setUniqueLabel(tableName);
                    System.out.println(tableInfo.generateClass());
                    String s = mySQLDataProcess.dataPack(tableDateList, tableInfo.toEasyTableAnnotation(false));
                    s = s.replaceAll("'true'", "1").
                            replaceAll("'false'", "0").
                            replaceAll("'null'", NormalUsedString.NULL);
                    file.write(s);
                    file.write(NormalUsedString.SEMICOLON);
                    file.newLine();
                }

            }
        }
        System.out.println("数据备份结束输出文件地址:"+upsertConfig.getCacheFileAddress());
        file.close();
    }
}
