package com.wu.freamwork.controller;


import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.process.MySQLDataProcess;
import com.wu.framework.easy.stereotype.upsert.util.FileUtil;
import com.wu.framework.easy.stereotype.web.EasyController;

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

    private final  LazyOperation layerOperation;
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
        // 获取数据库中所有的表
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) ";
        List<EasyHashMap> easyHashMaps = layerOperation.executeSQL(sql, EasyHashMap.class);

        BufferedWriter file = FileUtil.createFile(upsertConfig.getCacheFileAddress(), "hc升级sql脚本.sql");
        for (EasyHashMap easyHashMap : easyHashMaps) {
            String countSQL="select count(1) from %s ";
            String tableName = easyHashMap.get("TABLE_NAME").toString();
            Integer count = layerOperation.executeSQLForBean(String.format(countSQL, tableName.toString()), Integer.class);
            if(count!=0){
                String  selectSQL="select * from %s ";
                List<EasyHashMap> tableDateList = layerOperation.executeSQL(String.format(selectSQL, tableName), EasyHashMap.class);
                System.out.println(tableDateList);
                file.write("-- "+ tableName);
                file.newLine();
                EasyHashMap easyHashMap1 = tableDateList.get(0);
                easyHashMap1.setUniqueLabel(tableName);
                String s = mySQLDataProcess.dataPack(tableDateList, easyHashMap1.toEasyTableAnnotation(false));
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
}
