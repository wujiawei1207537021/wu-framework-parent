package com.wu.smart.acw.client.nocode.provider.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceInParamConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceInParamDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 接口输入参数
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Repository
public class InterfaceInParamRepositoryImpl implements InterfaceInParamRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> story(InterfaceInParam interfaceInParam) {
        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
        lazyLambdaStream.upsert(interfaceInParamDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> batchStory(List<InterfaceInParam> interfaceInParamList) {
        List<InterfaceInParamDO> interfaceInParamDOList = interfaceInParamList.stream().map(InterfaceInParamConverter::fromInterfaceInParam).collect(Collectors.toList());
        lazyLambdaStream.upsert(interfaceInParamDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> findOne(InterfaceInParam interfaceInParam) {
        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
        InterfaceInParam interfaceInParamOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(interfaceInParamDO), InterfaceInParam.class);
        return ResultFactory.successOf(interfaceInParamOne);
    }

    /**
     * describe 查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<List<InterfaceInParam>> findList(InterfaceInParam interfaceInParam) {
        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
        List<InterfaceInParam> interfaceInParamList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(interfaceInParamDO), InterfaceInParam.class);
        return ResultFactory.successOf(interfaceInParamList);
    }

    /**
     * describe 分页查询多个接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<LazyPage<InterfaceInParam>> findPage(int size, int current, InterfaceInParam interfaceInParam) {
        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<InterfaceInParam> interfaceInParamLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(interfaceInParamDO), lazyPage, InterfaceInParam.class);
        return ResultFactory.successOf(interfaceInParamLazyPage);
    }

    /**
     * describe 删除接口输入参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceInParam> remove(InterfaceInParam interfaceInParam) {
        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
        //  lazyLambdaStream.delete(interfaceInParamDO);
        return ResultFactory.successOf();
    }

}