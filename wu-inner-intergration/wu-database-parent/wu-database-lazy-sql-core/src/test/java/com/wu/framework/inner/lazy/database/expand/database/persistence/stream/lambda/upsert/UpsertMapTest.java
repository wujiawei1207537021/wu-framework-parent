package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.upsert;

import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;

public class UpsertMapTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        EasyHashMap<Object, Object> objectObjectEasyHashMap = new EasyHashMap<>();
        objectObjectEasyHashMap.setUniqueLabel("easy_upsert");
        objectObjectEasyHashMap.put("id", 12);
        lazyLambdaStream.upsert(objectObjectEasyHashMap);

    }
}
