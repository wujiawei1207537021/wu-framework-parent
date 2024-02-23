package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.execute;

import com.wu.framework.inner.lazy.database.domain.DataBaseUser;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/11/12 6:22 下午
 */
public class ExecuteOneTest {
    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setQueryString("select user.* from user where  user.age  >  18  and user.sex  =  '男' limit 1");
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        persistenceRepository.setResultClass(DataBaseUser.class);
        //  执行SQL: select user.* from user where  user.age  >  18  and user.sex  =  '男' limit 1
        DataBaseUser dataBaseUser = (DataBaseUser) lazyLambdaStream.executeOne(persistenceRepository);
        System.out.println(dataBaseUser);
    }
}
