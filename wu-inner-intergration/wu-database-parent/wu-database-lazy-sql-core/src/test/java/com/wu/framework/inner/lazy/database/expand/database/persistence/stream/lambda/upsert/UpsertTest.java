package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.upsert;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

public class UpsertTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUser dataBaseUser = new DataBaseUser();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址\\你好");
        dataBaseUser.setAge(18);
        String str = "地址\\你好";
        str = str.replaceAll("\\\\", "\\\\\\\\");
        System.out.println(str);

        // 同执行SQL: insert into user (id,username,birthday,sex,age,age_type,address_id) VALUES (null,'username',null,null,18,null,null)  ON DUPLICATE KEY UPDATE
        // id=values (id),username=values (username),birthday=values (birthday),sex=values (sex),age=values (age),age_type=values (age_type),address_id=values (address_id)
        lazyLambdaStream.upsert(dataBaseUser);

    }
}
