package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.lazy.database.domain.DataBaseAddress;
import com.wu.framework.inner.lazy.database.domain.DataBaseRole;
import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserDTO;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;

import java.util.Collection;

class SelectOneLazyLambdaStreamTest {

    static LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
            "127.0.0.1",
            3306,
            "acw",
            "root",
            "wujiawei"
    );

    public static void main(String[] args) {

        // 创造数据
//        lazyLambdaStream.smartUpsert(
//                DataTransformUntil.simulationBean(DataBaseUser.class),
//                DataTransformUntil.simulationBean(DataBaseAddress.class)
//                , DataTransformUntil.simulationBean(DataBaseRole.class)
//        );
        String userName = "紧";
        // 混合查询
        final Collection<DataBaseUser> collection
                = lazyLambdaStream.
                selectList(
                        LazyWrappers.<DataBaseUser>lambdaWrapper()
                                .eqIgnoreEmpty(DataBaseUser::getId, null)
                                .eq(DataBaseUser::getUsername, userName)
                                .eq(DataBaseUser::getAddress, "123456")
                                .leftJoin(
                                        LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoin()
                                                .eqRighto(DataBaseAddress::getId, "111")
                                                .eqo(DataBaseUser::getAddress, "111")
                                                .eq(DataBaseUser::getAddressId, DataBaseAddress::getId)
                                )
                                .rightJoin(LazyWrappers.<DataBaseUser, DataBaseRole>lambdaWrapperJoin().eq(DataBaseUser::getRoleId, DataBaseRole::getId))
                                .as(DataBaseUser::getSex, "hh")
                                .as(DataBaseUser::getAddress, DataBaseUserDTO::getUsername)
                                .or(LazyWrappers.<DataBaseUser, DataBaseAddress>lambdaWrapperJoinOr()
                                        .eqRighto(DataBaseAddress::getId, "111")
                                        .eqo(DataBaseUser::getAddress, "111")
                                        .eq(DataBaseUser::getAddressId, DataBaseAddress::getId)
                                )
                                .or(DataBaseUser::getAddress, "222")
                );
        System.out.println(collection);


    }
}