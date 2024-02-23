package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyOperationProxyFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

class AbstractInternalLinkSelectLazyLambdaStreamTest {

    static LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
            "127.0.0.1",
            3306,
            "acw",
            "root",
            "wujiawei"
    );

    public static void main(String[] args) {


        Collection<DataBaseUser> dataBaseUsers = lazyLambdaStream
                .selectList(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eq(DataBaseUser::getAge, 18).apply("id >1").
                                apply("id >5")
                );
        System.out.println(dataBaseUsers);
        // 符合查询条件


//        lazyLambdaStream.smartUpsert(DataTransformUntil.simulationBean(DataBaseAddress.class));

        LazySqlOperation lazySqlOperation = LazyOperationProxyFactory.createLazyOperation("127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei");
        Map strings = lazySqlOperation.executeSQLForBean("SELECT * FROM sys_user ; SELECT* FROM api;", Map.class);
        System.out.println(strings);

    }

    @Test
    void select() {


    }
}