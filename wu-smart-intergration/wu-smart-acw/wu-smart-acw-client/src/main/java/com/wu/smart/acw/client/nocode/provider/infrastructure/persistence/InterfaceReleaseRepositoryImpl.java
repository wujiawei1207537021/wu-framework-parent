package com.wu.smart.acw.client.nocode.provider.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceReleaseConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceReleaseDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe Dataway API 发布历史。
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Repository
public class InterfaceReleaseRepositoryImpl implements InterfaceReleaseRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> story(InterfaceRelease interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = InterfaceReleaseConverter.fromInterfaceRelease(interfaceRelease);
        lazyLambdaStream.upsert(interfaceReleaseDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> batchStory(List<InterfaceRelease> interfaceReleaseList) {
        List<InterfaceReleaseDO> interfaceReleaseDOList = interfaceReleaseList.stream().map(InterfaceReleaseConverter::fromInterfaceRelease).collect(Collectors.toList());
        lazyLambdaStream.upsert(interfaceReleaseDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> findOne(InterfaceRelease interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = InterfaceReleaseConverter.fromInterfaceRelease(interfaceRelease);
        InterfaceRelease interfaceReleaseOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(interfaceReleaseDO), InterfaceRelease.class);
        return ResultFactory.successOf(interfaceReleaseOne);
    }

    /**
     * describe 查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<List<InterfaceRelease>> findList(InterfaceRelease interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = InterfaceReleaseConverter.fromInterfaceRelease(interfaceRelease);
        List<InterfaceRelease> interfaceReleaseList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(interfaceReleaseDO), InterfaceRelease.class);
        return ResultFactory.successOf(interfaceReleaseList);
    }

    /**
     * describe 分页查询多个Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<LazyPage<InterfaceRelease>> findPage(int size, int current, InterfaceRelease interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = InterfaceReleaseConverter.fromInterfaceRelease(interfaceRelease);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<InterfaceRelease> interfaceReleaseLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(interfaceReleaseDO), lazyPage, InterfaceRelease.class);
        return ResultFactory.successOf(interfaceReleaseLazyPage);
    }

    /**
     * describe 删除Dataway API 发布历史。
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceRelease> remove(InterfaceRelease interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = InterfaceReleaseConverter.fromInterfaceRelease(interfaceRelease);
        //  lazyLambdaStream.delete(interfaceReleaseDO);
        return ResultFactory.successOf();
    }

}