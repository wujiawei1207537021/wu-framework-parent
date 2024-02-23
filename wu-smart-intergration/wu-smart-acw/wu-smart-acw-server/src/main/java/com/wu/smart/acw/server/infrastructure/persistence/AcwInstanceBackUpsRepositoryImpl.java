package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.AcwInstanceBackUpsRepository;
import com.wu.smart.acw.server.domain.model.database.instance.back.ups.DatabaseInstanceBackUps;
import com.wu.smart.acw.server.infrastructure.converter.DatabaseInstanceBackUpsConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwInstanceBackUpsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 数据库备份信息
 *
 * @author Jia wei Wu
 * @date 2023/07/09 11:24 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AcwInstanceBackUpsRepositoryImpl implements AcwInstanceBackUpsRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> story(DatabaseInstanceBackUps databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = DatabaseInstanceBackUpsConverter.fromDatabaseInstanceBackUps(databaseInstanceBackUps);
        lazyLambdaStream.upsert(acwInstanceBackUpsDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> batchStory(List<DatabaseInstanceBackUps> databaseInstanceBackUpsList) {
        List<AcwInstanceBackUpsDO> acwInstanceBackUpsDOList = databaseInstanceBackUpsList.stream().map(DatabaseInstanceBackUpsConverter::fromDatabaseInstanceBackUps).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwInstanceBackUpsDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> findOne(DatabaseInstanceBackUps databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = DatabaseInstanceBackUpsConverter.fromDatabaseInstanceBackUps(databaseInstanceBackUps);
        DatabaseInstanceBackUps databaseInstanceBackUpsOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwInstanceBackUpsDO), DatabaseInstanceBackUps.class);
        return ResultFactory.successOf(databaseInstanceBackUpsOne);
    }

    /**
     * describe 查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<List<DatabaseInstanceBackUps>> findList(DatabaseInstanceBackUps databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = DatabaseInstanceBackUpsConverter.fromDatabaseInstanceBackUps(databaseInstanceBackUps);
        List<DatabaseInstanceBackUps> databaseInstanceBackUpsList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwInstanceBackUpsDO), DatabaseInstanceBackUps.class);
        return ResultFactory.successOf(databaseInstanceBackUpsList);
    }

    /**
     * describe 分页查询多个数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<LazyPage<DatabaseInstanceBackUps>> findPage(int size, int current, DatabaseInstanceBackUps databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = DatabaseInstanceBackUpsConverter.fromDatabaseInstanceBackUps(databaseInstanceBackUps);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<DatabaseInstanceBackUps> databaseInstanceBackUpsLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwInstanceBackUpsDO), lazyPage, DatabaseInstanceBackUps.class);
        return ResultFactory.successOf(databaseInstanceBackUpsLazyPage);
    }

    /**
     * describe 删除数据库备份信息
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/09 11:24 上午
     **/

    @Override
    public Result<DatabaseInstanceBackUps> remove(DatabaseInstanceBackUps databaseInstanceBackUps) {
        AcwInstanceBackUpsDO acwInstanceBackUpsDO = DatabaseInstanceBackUpsConverter.fromDatabaseInstanceBackUps(databaseInstanceBackUps);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwInstanceBackUpsDO));
        return ResultFactory.successOf();
    }

}