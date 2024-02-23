package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstanceRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwRedisInstanceConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwRedisInstanceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class AcwRedisInstanceRepositoryImpl implements AcwRedisInstanceRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> story(AcwRedisInstance acwRedisInstance) {
        AcwRedisInstanceDO acwRedisInstanceDO = AcwRedisInstanceConverter.fromAcwRedisInstance(acwRedisInstance);
        lazyLambdaStream.upsert(acwRedisInstanceDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> batchStory(List<AcwRedisInstance> acwRedisInstanceList) {
        List<AcwRedisInstanceDO> acwRedisInstanceDOList = acwRedisInstanceList.stream().map(AcwRedisInstanceConverter::fromAcwRedisInstance).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwRedisInstanceDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> findOne(AcwRedisInstance acwRedisInstance) {
        AcwRedisInstanceDO acwRedisInstanceDO = AcwRedisInstanceConverter.fromAcwRedisInstance(acwRedisInstance);
        AcwRedisInstance acwRedisInstanceOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwRedisInstanceDO), AcwRedisInstance.class);
        return ResultFactory.successOf(acwRedisInstanceOne);
    }

    /**
     * describe 查询多个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<List<AcwRedisInstance>> findList(AcwRedisInstance acwRedisInstance) {
        AcwRedisInstanceDO acwRedisInstanceDO = AcwRedisInstanceConverter.fromAcwRedisInstance(acwRedisInstance);
        List<AcwRedisInstance> acwRedisInstanceList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwRedisInstanceDO), AcwRedisInstance.class);
        return ResultFactory.successOf(acwRedisInstanceList);
    }

    /**
     * describe 分页查询多个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<LazyPage<AcwRedisInstance>> findPage(int size, int current, AcwRedisInstance acwRedisInstance) {
        AcwRedisInstanceDO acwRedisInstanceDO = AcwRedisInstanceConverter.fromAcwRedisInstance(acwRedisInstance);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<AcwRedisInstance> acwRedisInstanceLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwRedisInstanceDO), lazyPage, AcwRedisInstance.class);
        return ResultFactory.successOf(acwRedisInstanceLazyPage);
    }

    /**
     * describe 删除Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> remove(AcwRedisInstance acwRedisInstance) {
        AcwRedisInstanceDO acwRedisInstanceDO = AcwRedisInstanceConverter.fromAcwRedisInstance(acwRedisInstance);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwRedisInstanceDO));
        return ResultFactory.successOf();
    }

}