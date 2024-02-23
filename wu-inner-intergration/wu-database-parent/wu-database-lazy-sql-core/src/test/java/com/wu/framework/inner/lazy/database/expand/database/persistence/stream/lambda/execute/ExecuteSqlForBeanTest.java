package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.execute;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/11/12 6:14 下午
 */
public class ExecuteSqlForBeanTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        //  执行SQL: select user.* from user where  user.age  >  18  and user.sex  =  '男' limit 1
        DataBaseUser dataBaseUser = lazyLambdaStream.executeSQLForBean("select user.* from user where  user.age  >  %s  and user.sex  =  '%s' limit 1", DataBaseUser.class, 18, "男");
        System.out.println(dataBaseUser);
    }
}
