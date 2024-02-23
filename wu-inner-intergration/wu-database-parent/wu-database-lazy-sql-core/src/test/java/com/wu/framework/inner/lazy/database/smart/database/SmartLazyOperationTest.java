package com.wu.framework.inner.lazy.database.smart.database;

import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

class SmartLazyOperationTest {

    static LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
            "127.0.0.1",
            3306,
            "acw",
            "root",
            "wujiawei"
    );

    public static void main(String[] args) {

    }
}