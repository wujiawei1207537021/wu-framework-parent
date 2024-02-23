package com.wu.freamwork;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.freamwork.domain.LazyUserTest;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LazyLambdaStreamTest {
    @Resource
    LazyLambdaStream lazyLambdaStream;


    /**
     * insert
     */
    @Test()
    public void insert() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setUsername("紧");
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setId(12L);
        lazyLambdaStream.insert(lazyUserTest);
        // insert into lazy_user_test (id,username,birthday,sex,age,is_deleted) VALUES (12,'紧',null,'MAN',null,null)
    }

    /**
     * upsert
     */
    @Test()
    public void upsert() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setUsername("紧");
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setId(12L);
        lazyLambdaStream.upsert(lazyUserTest);
        // insert into lazy_user_test (id,username,birthday,sex,age,is_deleted) VALUES (12,'紧',null,'MAN',null,null)  ON DUPLICATE KEY UPDATE username=values (username),birthday=values (birthday),sex=values (sex),age=values (age),is_deleted=values (is_deleted)
    }

    /**
     * 查询
     */
    @Test()
    public void select() {

        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setUsername("紧");
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setId(12L);
        lazyLambdaStream.upsert(lazyUserTest);
        lazyLambdaStream.update(lazyUserTest, LazyWrappers.<LazyUserTest>lambdaWrapper().eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN));

        List<LazyUserTest> lazyUserTests = lazyLambdaStream.selectList(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .inOr(LazyUserTest::getAge, List.of(18, 19, 20))
                .eq(LazyUserTest::getIsDeleted,false)
        );
        System.out.println("18、19、20 age user:" + lazyUserTests);
    }

    /**
     * count
     */
    @Test
    public void  count(){
        Long count = lazyLambdaStream.count(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getIsDeleted, false)
                .eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN)
                .gt(LazyUserTest::getAge, 10)
                .gt(LazyUserTest::getBirthday, LocalDateTime.now()));
        // 如同执行如下sql select count(1) from lazy_user_test where  lazy_user_test.is_deleted  = false  and  lazy_user_test.sex  = 'MAN'  and  lazy_user_test.age  > 10  and  lazy_user_test.birthday  > '2024-02-06 20:21:03'  and  lazy_user_test.is_deleted  = false
        System.out.println("lazyLambdaStream.count:"+count);
    }


    /**
     * exists
     */
    @Test
    public void  exists(){
        boolean exists = lazyLambdaStream.exists(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getIsDeleted, false)
                .eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN)
                .gt(LazyUserTest::getAge, 10)
                .gt(LazyUserTest::getBirthday, LocalDateTime.now()));
        // 如同执行如下sql select count(1) from lazy_user_test where  lazy_user_test.is_deleted  = false  and  lazy_user_test.sex  = 'MAN'  and  lazy_user_test.age  > 10  and  lazy_user_test.birthday  > '2024-02-06 20:21:03'  and  lazy_user_test.is_deleted  = false
        System.out.println("lazyLambdaStream.exists:"+exists);
    }

    /**
     * selectName
     */
    @Test
    public void  selectName(){
        String userName = lazyLambdaStream.selectOne(LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getId, 1)
                .eq(LazyUserTest::getIsDeleted, false)
                        .onlyUseAs()
                        .as(LazyUserTest::getUsername,LazyUserTest::getUsername),
                String.class);
        // SELECT  lazy_user_test.username as username  from lazy_user_test where  lazy_user_test.id  = 1  and  lazy_user_test.is_deleted  = false  and  lazy_user_test.is_deleted  = false
        System.out.println("只查询用户ID为1 的用户名称:"+userName);
    }

    /**
     * 只查询用户名称ID、年龄减少数据库IO
     */
    @Test
    public void  selectNameIdAge(){
        LazyUserTest userNameIdAge = lazyLambdaStream.selectOne(LazyWrappers.<LazyUserTest>lambdaWrapper()
                        .eq(LazyUserTest::getId, 1)
                        .eq(LazyUserTest::getIsDeleted, false)
                        .onlyUseAs()

                        .as(LazyUserTest::getId,LazyUserTest::getId)
                        .as(LazyUserTest::getAge,LazyUserTest::getAge)
                        .as(LazyUserTest::getUsername,LazyUserTest::getUsername)
                ,
                LazyUserTest.class);
        // SELECT  lazy_user_test.id as id , lazy_user_test.username as username , lazy_user_test.age as age  from lazy_user_test where  lazy_user_test.id  = 1  and  lazy_user_test.is_deleted  = false  and  lazy_user_test.is_deleted  = false
        System.out.println("只查询用户ID为1 的用户名称、ID、age:"+userNameIdAge);
    }




}
