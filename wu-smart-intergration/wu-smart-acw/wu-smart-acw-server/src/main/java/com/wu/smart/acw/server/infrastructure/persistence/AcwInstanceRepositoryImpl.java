package com.wu.smart.acw.server.infrastructure.persistence;

import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.DynamicAdapter;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstance;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwInstanceConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwInstanceDO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AcwInstanceRepositoryImpl implements AcwInstanceRepository {
    private final DynamicAdapter dynamicAdapter;
    private final LazyLambdaStream lazyLambdaStream;

    public AcwInstanceRepositoryImpl(DynamicAdapter dynamicAdapter, LazyLambdaStream lazyLambdaStream) {
        this.dynamicAdapter = dynamicAdapter;
        this.lazyLambdaStream = lazyLambdaStream;
    }


    /**
     * describe 新增数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwInstance> story(AcwInstance acwInstance) {
        AcwInstanceDO acwInstanceDO = AcwInstanceConverter.INSTANCE.fromAcwInstance(acwInstance);
        lazyLambdaStream.upsert(acwInstanceDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwInstance> batchStory(List<AcwInstance> acwInstanceList) {
        List<AcwInstanceDO> acwInstanceDOList = acwInstanceList.stream().map(AcwInstanceConverter.INSTANCE::fromAcwInstance).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwInstanceDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwInstance> findOne(AcwInstance acwInstance) {
        AcwInstanceDO acwInstanceDO = AcwInstanceConverter.INSTANCE.fromAcwInstance(acwInstance);
        AcwInstance acwInstanceOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwInstanceDO), AcwInstance.class);
        return ResultFactory.successOf(acwInstanceOne);
    }

    /**
     * describe 查询多个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<List<AcwInstance>> findList(AcwInstance acwInstance) {
        AcwInstanceDO acwInstanceDO = AcwInstanceConverter.INSTANCE.fromAcwInstance(acwInstance);
        List<AcwInstance> acwInstanceList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwInstanceDO), AcwInstance.class);
        return ResultFactory.successOf(acwInstanceList);
    }

    /**
     * describe 分页查询多个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<LazyPage<AcwInstance>> findPage(int size, int current, AcwInstance acwInstance) {
        AcwInstanceDO acwInstanceDO = AcwInstanceConverter.INSTANCE.fromAcwInstance(acwInstance);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<AcwInstance> acwInstanceLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwInstanceDO), lazyPage, AcwInstance.class);
        return ResultFactory.successOf(acwInstanceLazyPage);
    }

    /**
     * describe 删除数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 03:15 下午
     **/

    @Override
    public Result<AcwInstance> remove(AcwInstance acwInstance) {
        AcwInstanceDO acwInstanceDO = AcwInstanceConverter.INSTANCE.fromAcwInstance(acwInstance);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwInstanceDO));
        return ResultFactory.successOf();
    }

    /**
     * 切换实例
     *
     * @param instanceId
     * @return
     */
    @Override
    public AcwInstanceUo switchInstance(String instanceId) {
        AcwInstanceUo acwInstanceUo = lazyLambdaStream.selectOne(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                .eq(AcwInstanceUo::getId, instanceId)
                .eq(AcwInstanceUo::getIsDeleted, false));
        if (ObjectUtils.isEmpty(acwInstanceUo)) {
            RuntimeExceptionFactory.of("无法找到数据源");
        }
        LazyDataSourceProperties lazyDataSourceProperties = new LazyDataSourceProperties();
        lazyDataSourceProperties.setUrl(acwInstanceUo.getUrl());
        lazyDataSourceProperties.setUsername(acwInstanceUo.getUsername());
        lazyDataSourceProperties.setPassword(acwInstanceUo.getPassword());
        lazyDataSourceProperties.setAlias(acwInstanceUo.getId());
        dynamicAdapter.loading(lazyDataSourceProperties);
        ClassLazyDynamicEndpoint classLazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        classLazyDynamicEndpoint.setName(acwInstanceUo.getId());
        DynamicLazyDSContextHolder.push(classLazyDynamicEndpoint);
        return acwInstanceUo;
    }
}
