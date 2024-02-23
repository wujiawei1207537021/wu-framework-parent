package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.perfict;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.database.domain.DataBaseAddress;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;


public class Perfect {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        LazyDatabaseJsonMessage.lazyDataSourceType = LazyDataSourceType.MySQL;
        // 完善表结构
        lazyLambdaStream.createTable(DataBaseAddress.class);

    }
}
