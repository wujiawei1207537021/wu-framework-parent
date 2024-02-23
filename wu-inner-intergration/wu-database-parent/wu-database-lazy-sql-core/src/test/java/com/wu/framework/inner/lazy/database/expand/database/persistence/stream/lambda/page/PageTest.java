package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.page;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/11/12 6:09 下午
 */
public class PageTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        //  执行SQL: select user.* from user where  user.age  >  18  and user.sex  =  '男'
        LazyPage<DataBaseUser> dataBaseUserLazyPage = lazyLambdaStream.selectPage(LazyWrappers.<DataBaseUser>lambdaWrapper()
                        .gt(DataBaseUser::getAge, 18)
                        .eq(DataBaseUser::getSex, "男"),
                new LazyPage<>(1, 10)
        );
        System.out.println(dataBaseUserLazyPage);
    }
}
