package com.wu.freamwork.controller;


import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.database.expand.database.persistence.LayerOperation;
import com.wu.framework.inner.database.test.dao.IUserDao;
import com.wu.framework.inner.database.test.pojo.DataBaseUser;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
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

    @Resource
    private LayerOperation layerOperation;


    //    @CDS("localhost")
    @Override
    public void run(String... args) throws Exception {
        upsert();
//        insert();
//        update();
//        delete();
//        select();
//        System.out.println(iUserDao.findAll());
//        SQLConverter.createSelectSQL(OmTpsmPubOthEqpOpemngVehicleRegistration.class);
        List<DataBaseUser> ss = layerOperation.executeSQL("select * from user", DataBaseUser.class);
        System.out.println(ss);
        layerOperation.activeUpsert(new DataBaseUser());
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
