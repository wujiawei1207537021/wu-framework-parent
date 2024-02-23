package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.insert;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/11/12 5:59 下午
 */
public class InsertOrUpdateTest {
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
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        // 如果存在 如同执行 update
        // 不存在 同执行SQL: INSERT INTO user(username,birthday,sex,age,age_type,address_id)values(null,'username',null,null,'18',null,null)
        lazyLambdaStream.saveOrUpdate(dataBaseUser);

    }
}
