package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstanceRepository;
import com.wu.smart.acw.server.infrastructure.converter.AcwClientInstanceConverter;
import com.wu.smart.acw.server.infrastructure.entity.AcwClientInstanceDO;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class AcwClientInstanceRepositoryImpl  implements AcwClientInstanceRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstance 新增客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> story(AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        lazyLambdaStream.upsert(acwClientInstanceDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增客户端实例
     *
     * @param acwClientInstanceList 批量新增客户端实例     
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<List<AcwClientInstance>> batchStory(List<AcwClientInstance> acwClientInstanceList) {
        List<AcwClientInstanceDO> acwClientInstanceDOList = acwClientInstanceList.stream().map(AcwClientInstanceConverter.INSTANCE::fromAcwClientInstance).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwClientInstanceDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个客户端实例
     *
     * @param acwClientInstance 查询单个客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> findOne(AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        AcwClientInstance acwClientInstanceOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwClientInstanceDO), AcwClientInstance.class);
        return ResultFactory.successOf(acwClientInstanceOne);
    }

    /**
     * describe 查询多个客户端实例
     *
     * @param acwClientInstance 查询多个客户端实例     
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<List<AcwClientInstance>> findList(AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        List<AcwClientInstance> acwClientInstanceList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwClientInstanceDO), AcwClientInstance.class);
        return ResultFactory.successOf(acwClientInstanceList);
    }

    /**
     * describe 分页查询多个客户端实例
     *
     * @param size 当前页数
     * @param current 当前页
     * @param acwClientInstance 分页查询多个客户端实例     
     * @return {@link Result<LazyPage<AcwClientInstance>>} 分页客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<LazyPage<AcwClientInstance>> findPage(int size,int current,AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        LazyPage<AcwClientInstance> lazyPage = new LazyPage<>(current,size);
        LazyPage<AcwClientInstance> acwClientInstanceLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwClientInstanceDO),lazyPage, AcwClientInstance.class);
        return ResultFactory.successOf(acwClientInstanceLazyPage);
    }

    /**
     * describe 删除客户端实例
     *
     * @param acwClientInstance 删除客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> remove(AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwClientInstanceDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在客户端实例
     *
     * @param acwClientInstance 客户端实例领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<Boolean> exists(AcwClientInstance acwClientInstance) {
        AcwClientInstanceDO acwClientInstanceDO = AcwClientInstanceConverter.INSTANCE.fromAcwClientInstance(acwClientInstance);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(acwClientInstanceDO));
        return ResultFactory.successOf(exists);
    }

}