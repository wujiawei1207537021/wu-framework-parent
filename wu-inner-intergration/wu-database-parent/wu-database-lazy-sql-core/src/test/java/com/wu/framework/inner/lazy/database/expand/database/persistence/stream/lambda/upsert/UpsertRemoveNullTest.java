package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.upsert;

import com.wu.framework.inner.lazy.database.domain.DataBaseAddress;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

import java.util.ArrayList;
import java.util.List;

public class UpsertRemoveNullTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseAddress dataBaseAddress = new DataBaseAddress();
        dataBaseAddress.setId(1L);
        dataBaseAddress.setLongitude(1.2d);
        //  执行SQL: insert into dataBaseAddress (id,latitude,longitude) VALUES (1,'0.0','1.2')  ON DUPLICATE KEY UPDATE
        // id=values (id),latitude=values (latitude),longitude=values (longitude)
        lazyLambdaStream.upsertRemoveNull(dataBaseAddress);

        List<DataBaseAddress> dataBaseAddresses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataBaseAddress addressa = new DataBaseAddress();
            dataBaseAddress.setId(1L);
            dataBaseAddress.setLongitude(1.2d);
            dataBaseAddresses.add(addressa);
        }
        // 执行SQL: insert into dataBaseAddress (id,name,latitude,longitude) VALUES (null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0'),(null,null,'0.0','0.0')  ON DUPLICATE KEY UPDATE
        // id=values (id),name=values (name),latitude=values (latitude),longitude=values (longitude)
        lazyLambdaStream.upsert(dataBaseAddresses);

    }
}
