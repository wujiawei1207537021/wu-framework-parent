package com.wu.smart.acw.server.infrastructure.persistence;

import com.wu.smart.acw.server.infrastructure.entity.AcwClientJavaPathDO;
import com.wu.smart.acw.server.infrastructure.converter.AcwClientJavaPathConverter;
import com.wu.smart.acw.server.infrastructure.mapper.AcwClientJavaPathMapper;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPathRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class AcwClientJavaPathRepositoryImpl  implements  AcwClientJavaPathRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 新增客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<AcwClientJavaPath> story(AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        lazyLambdaStream.upsert(acwClientJavaPathDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathList 批量新增客户端使用创建Java代码常用路径     
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<List<AcwClientJavaPath>> batchStory(List<AcwClientJavaPath> acwClientJavaPathList) {
        List<AcwClientJavaPathDO> acwClientJavaPathDOList = acwClientJavaPathList.stream().map(AcwClientJavaPathConverter.INSTANCE::fromAcwClientJavaPath).collect(Collectors.toList());
        lazyLambdaStream.upsert(acwClientJavaPathDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 查询单个客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<AcwClientJavaPath> findOne(AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        AcwClientJavaPath acwClientJavaPathOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(acwClientJavaPathDO), AcwClientJavaPath.class);
        return ResultFactory.successOf(acwClientJavaPathOne);
    }

    /**
     * describe 查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<List<AcwClientJavaPath>> findList(AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        List<AcwClientJavaPath> acwClientJavaPathList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(acwClientJavaPathDO), AcwClientJavaPath.class);
        return ResultFactory.successOf(acwClientJavaPathList);
    }

    /**
     * describe 分页查询多个客户端使用创建Java代码常用路径
     *
     * @param size 当前页数
     * @param current 当前页
     * @param acwClientJavaPath 分页查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result<LazyPage<AcwClientJavaPath>>} 分页客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<LazyPage<AcwClientJavaPath>> findPage(int size,int current,AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        LazyPage<AcwClientJavaPath> lazyPage = new LazyPage<>(current,size);
        LazyPage<AcwClientJavaPath> acwClientJavaPathLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(acwClientJavaPathDO),lazyPage, AcwClientJavaPath.class);
        return ResultFactory.successOf(acwClientJavaPathLazyPage);
    }

    /**
     * describe 删除客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 删除客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<AcwClientJavaPath> remove(AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(acwClientJavaPathDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPath 客户端使用创建Java代码常用路径领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2023/12/08 06:04 晚上
     **/

    @Override
    public Result<Boolean> exists(AcwClientJavaPath acwClientJavaPath) {
        AcwClientJavaPathDO acwClientJavaPathDO = AcwClientJavaPathConverter.INSTANCE.fromAcwClientJavaPath(acwClientJavaPath);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(acwClientJavaPathDO));
        return ResultFactory.successOf(exists);
    }

}