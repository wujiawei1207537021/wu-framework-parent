package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.insert;

import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserHasOtherBean;
import com.wu.framework.inner.lazy.database.domain.DataBaseUserInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/11/12 5:59 下午
 */
public class InsertBeanHasOtherBeanTest {
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        DataBaseUserHasOtherBean dataBaseUser = new DataBaseUserHasOtherBean();
        dataBaseUser.setUsername("username");
        dataBaseUser.setAddress("地址");
        dataBaseUser.setAge(18);
        dataBaseUser.setDataBaseUserInfo(DataTransformUntil.simulationBean(DataBaseUserInfo.class));
        // 同执行SQL: INSERT INTO user(username,birthday,sex,age,age_type,address_id)values(null,'username',null,null,'18',null,null)
        lazyLambdaStream.insert(dataBaseUser);

    }
}
