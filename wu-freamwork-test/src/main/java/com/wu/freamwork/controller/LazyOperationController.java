package com.wu.freamwork.controller;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.authorization.model.User;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.LazyDatabase;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.LazyOperationAutoStuffed;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/4/17 10:37 下午
 */
@EasyController("/public/lazy")
public class LazyOperationController implements CommandLineRunner {

    public final PerfectLazyOperation perfectLazyOperation;
    public final LazyLambdaStream lambdaStream;
    private final LazyOperation lazyOperation;
    private final LazyOperationAutoStuffed operationAutoFill;

    SimpleAsyncTaskExecutor consumerExecutor = new SimpleAsyncTaskExecutor("LAZY-C-");

    public LazyOperationController(LazyOperation lazyOperation, PerfectLazyOperation perfectLazyOperation, LazyLambdaStream lambdaStream, LazyOperationAutoStuffed operationAutoFill) {
        this.lazyOperation = lazyOperation;
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

        final List<EasyHashMap> lazy = lazyOperation.executeSQL(sqlSelectTable, EasyHashMap.class, "lazy", "lazy");
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
        lazyOperation.smartUpsert(dataBaseUserList.get(0), dataBaseUserList.get(2), dataBaseUserList.get(3));
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
        lazyOperation.upsert(dataBaseUserList);
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
        lazyOperation.insert(dataBaseUserList);
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
            lazyOperation.updateById(dataBaseUser);
        }
//        lazyOperation.updateAllByIdList(dataBaseUserList);
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
//        lazyOperation.deleteById(dataBaseUser);
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
        lazyOperation.smartUpsert(dataBaseUser);
//        DataBaseUser dataBaseUser1 = lazyOperation.selectOne(dataBaseUser);
//        System.out.println(dataBaseUser1);

        dataBaseUser.setUsername(null);
//        List<DataBaseUser> dataBaseUserList = lazyOperation.selectAll(dataBaseUser);
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
        Page<DataBaseUser> page = lazyOperation.page(new Page(), DataBaseUser.class, null);
        System.out.println("分页查询:" + page.getPages());
    }

    /**
     * @param
     * @return describe 滚动查询
     * @author Jia wei Wu
     * @date 2021/4/18 7:31 下午
     **/
    public void scroll() throws Exception {
        perfectLazyOperation.scroll(null, DataBaseUser.class, null, m -> {
            System.out.println(m.getCurrent());
            return m;
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

    public void lambdaStream() {
        final Collection<User> collection = lambdaStream
                .of(User.class).select(LazyWrappers.<User>wrapper()
                                //                .leftJoin(BasicComparison.<User>wrapper().eq("hh","哈哈哈"))
                                .eq(User::getUsername, "admin")
                                .gt(User::getId, "10")
                                .lt(User::getId, 12)
//                .leftJoin(BasicComparison.<User>wrapper().eq("","")))

                ).collection(User.class);


        System.out.println(collection);
    }
}
