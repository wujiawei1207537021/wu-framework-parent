package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.transactional;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;

/**
 * 测试事务问题
 */
public class TransactionalTest {


    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );

        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setQueryString("update sys_user set sys_user.create_time=now() ");
        persistenceRepository.setExecutionType(LambdaTableType.UPDATE);
        persistenceRepository.setResultClass(Long.class);
        //  执行SQL: select user.* from user where  user.age  >  18  and user.sex  =  '男' limit 1
        Long dataBaseUser = (Long) lazyLambdaStream.executeOne(persistenceRepository);
        System.out.println(dataBaseUser);
    }

}
