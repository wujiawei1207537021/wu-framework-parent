package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.AcwSchemaBackUpsRepository;
import com.wu.smart.acw.server.domain.model.database.schema.back.ups.DatabaseSchemaBackUps;
import com.wu.smart.acw.server.infrastructure.converter.DatabaseSchemaBackUpsConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwSchemaBackUpsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 03:49 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AcwSchemaBackUpsRepositoryImpl implements AcwSchemaBackUpsRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> story(DatabaseSchemaBackUps databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = DatabaseSchemaBackUpsConverter.fromDatabaseSchemaBackUps(databaseSchemaBackUps);
        lazyLambdaStream.upsert(acwSchemaBackUpsDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> batchStory(List<DatabaseSchemaBackUps> databaseSchemaBackUpsList) {
        List<AcwSchemaBackUpsDO> acwSchemaBackUpsDOList = databaseSchemaBackUpsList.stream().map(DatabaseSchemaBackUpsConverter::fromDatabaseSchemaBackUps).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwSchemaBackUpsDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> findOne(DatabaseSchemaBackUps databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = DatabaseSchemaBackUpsConverter.fromDatabaseSchemaBackUps(databaseSchemaBackUps);
        DatabaseSchemaBackUps databaseSchemaBackUpsOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwSchemaBackUpsDO), DatabaseSchemaBackUps.class);
        return ResultFactory.successOf(databaseSchemaBackUpsOne);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<List<DatabaseSchemaBackUps>> findList(DatabaseSchemaBackUps databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = DatabaseSchemaBackUpsConverter.fromDatabaseSchemaBackUps(databaseSchemaBackUps);
        List<DatabaseSchemaBackUps> databaseSchemaBackUpsList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwSchemaBackUpsDO), DatabaseSchemaBackUps.class);
        return ResultFactory.successOf(databaseSchemaBackUpsList);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<LazyPage<DatabaseSchemaBackUps>> findPage(int size, int current, DatabaseSchemaBackUps databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = DatabaseSchemaBackUpsConverter.fromDatabaseSchemaBackUps(databaseSchemaBackUps);
        LazyPage<DatabaseSchemaBackUps> lazyPage = new LazyPage<>(current, size);
        LazyPage<DatabaseSchemaBackUps> databaseSchemaBackUpsLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwSchemaBackUpsDO), lazyPage, DatabaseSchemaBackUps.class);
        return ResultFactory.successOf(databaseSchemaBackUpsLazyPage);
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 03:49 下午
     **/

    @Override
    public Result<DatabaseSchemaBackUps> remove(DatabaseSchemaBackUps databaseSchemaBackUps) {
        AcwSchemaBackUpsDO acwSchemaBackUpsDO = DatabaseSchemaBackUpsConverter.fromDatabaseSchemaBackUps(databaseSchemaBackUps);
          lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwSchemaBackUpsDO));
        return ResultFactory.successOf();
    }

}