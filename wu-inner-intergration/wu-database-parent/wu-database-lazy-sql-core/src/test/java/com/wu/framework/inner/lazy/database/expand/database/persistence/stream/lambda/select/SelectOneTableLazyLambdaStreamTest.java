package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserDTO;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;

class SelectOneTableLazyLambdaStreamTest {

    static LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
            "127.0.0.1",
            3306,
            "acw",
            "root",
            "wujiawei"
    );

    public static void main(String[] args) {

        String userName = "紧";

        // 创造数据
        DataBaseUser dataBaseUser = DataTransformUntil.simulationBean(DataBaseUser.class);
        dataBaseUser.setAddress("123456");
        dataBaseUser.setUsername(userName);
        lazyLambdaStream.upsert(dataBaseUser);


        /***
         * 查询address =123456 username=紧  而后将sex 赋值给DataBaseUser 的hh上，将字段address 赋值给DataBaseUser username
         */
        DataBaseUser collection
                = lazyLambdaStream.
                selectOne(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eqIgnoreEmpty(DataBaseUser::getId, null)
                                .eq(DataBaseUser::getUsername, userName)
                                .eq(DataBaseUser::getAddress, "123456")
                                .as(DataBaseUser::getSex, "hh")
                                .as(DataBaseUser::getAddress, DataBaseUserDTO::getUsername)
                );
        System.out.println(collection);


    }
}