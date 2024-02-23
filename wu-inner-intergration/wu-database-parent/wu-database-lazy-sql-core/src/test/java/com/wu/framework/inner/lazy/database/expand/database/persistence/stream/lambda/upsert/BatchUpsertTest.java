package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.upsert;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BatchUpsertTest {

    public static Logger logger = LoggerFactory.getLogger(BatchUpsertTest.class);

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        List<DataBaseUser> dataBaseUserList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            DataBaseUser dataBaseUser = new DataBaseUser();
            dataBaseUser.setUsername("username");
            dataBaseUser.setAddress("地址");
            dataBaseUser.setAge(18);
            dataBaseUserList.add(dataBaseUser);
        }

        logger.info("starter upsert");
        // 同执行SQL: insert into user (id,username,birthday,sex,age,age_type,address_id) VALUES (null,'username',null,null,18,null,null)  ON DUPLICATE KEY UPDATE
        // id=values (id),username=values (username),birthday=values (birthday),sex=values (sex),age=values (age),age_type=values (age_type),address_id=values (address_id)
        lazyLambdaStream.upsert(dataBaseUserList);
        logger.info("end upsert");
    }
}
