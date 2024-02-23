package com.wu.freamwork.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationSmartAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.freamwork.domain.DataBaseUser;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/4/17 10:37 下午
 */
@EasyController("/public/lazy")
public class TestLazyOperationController implements CommandLineRunner {

    public final PerfectLazyOperation perfectLazyOperation;
    public final LazyLambdaStream lambdaStream;

    private final LazyOperationSmartAutoStuffed operationAutoFill;

    SimpleAsyncTaskExecutor consumerExecutor = new SimpleAsyncTaskExecutor("LAZY-C-");

    public TestLazyOperationController(PerfectLazyOperation perfectLazyOperation, LazyLambdaStream lambdaStream, LazyOperationSmartAutoStuffed operationAutoFill) {
        this.perfectLazyOperation = perfectLazyOperation;
        this.lambdaStream = lambdaStream;
        this.operationAutoFill = operationAutoFill;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        consumerExecutor.submit(new Thread() {
            /**
             * If this thread was constructed using a separate
             * <code>Runnable</code> run object, then that
             * <code>Runnable</code> object's <code>run</code> method is called;
             * otherwise, this method does nothing and returns.
             * <p>
             * Subclasses of <code>Thread</code> should override this method.
             *
             * @see #start()
             * @see #stop()
             */
            @SneakyThrows
            @Override
            public void run() {
//                perfectLazyOperation.saveSqlFile();
            }
        });

//        perfectLazyOperation.saveSqlFile();
//        System.out.println("数据导出成功");
//        lambdaStream();
//        fillLazySysUser();
//        insertOne();
//        stuffedOnes();
//        operationAutoFill.stuffedOne(User.class, 1000L);
//        System.out.println("数据填充结束");
    }

    public void test() throws Exception {
        smartUpsert();
        upsert();
        insert();
        update();
        delete();
        select();
        page();
        scroll();

        saveSqlFile();
    }


    /**
     * 数据填充
     */
    public void stuffedOnes() {
        for (LazyDatabase showDatabase : perfectLazyOperation.showDatabases()) {
            System.out.println("计划执行数据库填充" + showDatabase.getDatabase());
            for (LazyTableInfo showTable : perfectLazyOperation.showTables(showDatabase.getDatabase())) {
                operationAutoFill.stuffed(showTable, 10L);
            }
        }

    }

    /**
     * describe 填充 lazy.sys_user
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/16 3:42 下午
     **/
    public void fillLazySysUser() {
        final LazyTableInfo lazyTableInfo = new LazyTableInfo();
        lazyTableInfo.setTableSchema("lazy");
        lazyTableInfo.setTableName("sys_user");
        operationAutoFill.stuffed(lazyTableInfo, 1000L);
    }


    /**
     * 查询所有的表
     */
    public void showTables() {
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";

        final List<EasyHashMap> lazy = lambdaStream.executeSQL(sqlSelectTable, EasyHashMap.class, "lazy", "lazy");
        System.out.println(JSONObject.toJSONString(lazy));
    }

    /**
     * description 灵性添加
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/27 3:55 下午
     */
    public void smartUpsert() {
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
        lambdaStream.upsert(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("smartUpsert共计用时：" + (e - s) + "ms");
    }


    /**
     * 更新或者插入
     *
     * @params
     * @author Jia wei Wu
     * @date 2020/7/5 下午1:55
     **/
//    @Scheduled(fixedRate = 1000)
    public void upsert() {
        long s = System.currentTimeMillis();
        List<DataBaseUser> dataBaseUserList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setAddress("address");
            dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
            dataBaseUser.setSex("woman");
            dataBaseUser.setUsername("methodName" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        lambdaStream.upsert(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("upsert共计用时：" + (e - s) + "ms");
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
        for (int i = 0; i < 10000; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setAddress("address");
            dataBaseUser.setBirthday(LocalDateTime.now().toString());
//            dataBaseUser.setId(12);
            dataBaseUser.setSex("woman");
            dataBaseUser.setUsername("methodName" + i);
            dataBaseUserList.add(dataBaseUser);
        }
        lambdaStream.insert(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("insert共计用时：" + (e - s) + "ms");
    }

    public void insertOne() {
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setAddress("address");
        dataBaseUser.setBirthday(LocalDateTime.now().toString());
        dataBaseUser.setSex("woman");
        dataBaseUser.setUsername("methodName");
        dataBaseUser.setId(1200);
        lambdaStream.insert(dataBaseUser);
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
            lambdaStream.upsert(dataBaseUser);
        }
//        lazySqlOperation.updateAllByIdList(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("update共计用时：" + (e - s) + "ms");
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
        dataBaseUser.setId(12);
        dataBaseUser.setSex("woman");
        dataBaseUser.setUsername("methodName");
//        lazySqlOperation.deleteById(dataBaseUser);
        long e = System.currentTimeMillis();
        System.out.println("delete共计用时：" + (e - s) + "ms");
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
        lambdaStream.upsert(dataBaseUser);
//        DataBaseUser dataBaseUser1 = lazySqlOperation.selectOne(dataBaseUser);
//        System.out.println(dataBaseUser1);

        dataBaseUser.setUsername(null);
//        List<DataBaseUser> dataBaseUserList = lazySqlOperation.selectAll(dataBaseUser);
//        System.out.println(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("select 共计用时：" + (e - s) + "ms");
    }

    /**
     * @param
     * @return describe 分页查询
     * @author Jia wei Wu
     * @date 2021/4/18 12:50 下午
     **/
    public void page() {
        LazyPage<DataBaseUser> lazyPage = lambdaStream.selectPage(new LazyPage(), DataBaseUser.class, null);
        System.out.println("分页查询:" + lazyPage.getPages());
    }

    /**
     * @param
     * @return describe 滚动查询
     * @author Jia wei Wu
     * @date 2021/4/18 7:31 下午
     **/
    public void scroll() throws Exception {
        lambdaStream.scroll(null, LazyWrappers.<DataBaseUser>lambdaWrapper(), m -> {
            System.out.println(m.getCurrent());
        });
    }


    /**
     * description 数据迁移
     *
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/5/8 3:58 下午
     */
    public void saveSqlFile() throws ProcessException, IOException, SQLException, MethodParamFunctionException, ExecutionException, InterruptedException {
        perfectLazyOperation.saveSqlFile("mysql");
    }

//    public void lambdaStream() {
//        final Collection<User> collection = lambdaStream
//                .of(User.class).select(LazyWrappers.<User>lambdaWrapper()
//                                //                .leftJoin(BasicComparison.<User>wrapper().eq("hh","哈哈哈"))
//                                .eq(User::getUsername, "admin")
//                                .gt(User::getId, "10")
//                                .lt(User::getId, 12)
////                .leftJoin(BasicComparison.<User>wrapper().eq("","")))
//
//                ).collection(User.class);
//
//
//        System.out.println(collection);
//    }
}
