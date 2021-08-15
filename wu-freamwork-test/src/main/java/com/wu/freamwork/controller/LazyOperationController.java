package com.wu.freamwork.controller;

import com.wu.framework.authorization.model.AccessToken;
import com.wu.framework.authorization.model.User;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.PerfectLazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LambdaStream;
import com.wu.framework.inner.lazy.database.test.pojo.DataBaseUser;
import org.springframework.boot.CommandLineRunner;

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
 * @describe :
 * @date : 2021/4/17 10:37 下午
 */
@EasyController("/public/lazy")
public class LazyOperationController implements CommandLineRunner {

    private final LazyOperation lazyOperation;
    public final PerfectLazyOperation perfectLazyOperation;
    public final LambdaStream lambdaStream;

    public LazyOperationController(LazyOperation lazyOperation, PerfectLazyOperation perfectLazyOperation, LambdaStream lambdaStream) {
        this.lazyOperation = lazyOperation;
        this.perfectLazyOperation = perfectLazyOperation;
        this.lambdaStream = lambdaStream;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
//        perfectLazyOperation.saveSqlFile();
//        perfectLazyOperation.saveSqlFile();
//        System.out.println("数据导出成功");
        lambdaStream();
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
     * description 灵性添加
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
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
        lazyOperation.deleteById(dataBaseUser);
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
        List<DataBaseUser> dataBaseUserList = lazyOperation.selectAll(dataBaseUser);
        System.out.println(dataBaseUserList);
        long e = System.currentTimeMillis();
        System.out.println("select 共计用时：" + (e - s) + "ms");
    }

    /**
     * @param
     * @return
     * @describe 分页查询
     * @author Jia wei Wu
     * @date 2021/4/18 12:50 下午
     **/
    public void page() {
        Page<DataBaseUser> page = lazyOperation.page(new Page(), DataBaseUser.class, null);
        System.out.println("分页查询:" + page.getPages());
    }

    /**
     * @param
     * @return
     * @describe 滚动查询
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
     * @author 吴佳伟
     * @date 2021/5/8 3:58 下午
     */
    public void saveSqlFile() throws ProcessException, IOException, SQLException, MethodParamFunctionException, ExecutionException, InterruptedException {
        perfectLazyOperation.saveSqlFile("mysql");
    }

    public void lambdaStream() {
        final Collection<User> collection = lambdaStream.select()
                .table(User.class)
                .eq("username","admin")
                .gt("id","10")
                .lt("id","20")
                .collection(User.class);


        System.out.println(collection);
    }
}
