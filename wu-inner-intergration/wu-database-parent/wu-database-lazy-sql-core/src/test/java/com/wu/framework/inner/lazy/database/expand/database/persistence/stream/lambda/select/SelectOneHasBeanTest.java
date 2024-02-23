package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.select;

import com.wu.framework.inner.layer.util.JsonUtils;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserHasOtherBean;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

import java.util.Collection;

class SelectOneHasBeanTest {

    static LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
            "127.0.0.1",
            3306,
            "acw",
            "root",
            "wujiawei"
    );

    public static void main(String[] args) {

        String json = "{\"id\":-1685671218,\"username\":\"盒夷\",\"birthday\":\"喻\",\"sex\":\"\",\"address\":\"青\",\"age\":1575374454,\"ageType\":1558423040,\"addressId\":-967099702,\"roleId\":1918353476}";
        DataBaseUserInfo dataBaseUserInfo = JsonUtils.parseObject(json, DataBaseUserInfo.class);
        Collection<DataBaseUserHasOtherBean> dataBaseUserHasOtherBeans = lazyLambdaStream.of(DataBaseUserHasOtherBean.class).selectList(null);
        System.out.println(dataBaseUserHasOtherBeans);

    }
}