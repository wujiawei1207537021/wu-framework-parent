package com.wu.smart.acw.client.nocode.provider.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceOutParamConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceOutParamDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 接口输出参数
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Repository
public class InterfaceOutParamRepositoryImpl implements InterfaceOutParamRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> story(InterfaceOutParam interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
        lazyLambdaStream.upsert(interfaceOutParamDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> batchStory(List<InterfaceOutParam> interfaceOutParamList) {
        List<InterfaceOutParamDO> interfaceOutParamDOList = interfaceOutParamList.stream().map(InterfaceOutParamConverter::fromInterfaceOutParam).collect(Collectors.toList());
        lazyLambdaStream.upsert(interfaceOutParamDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> findOne(InterfaceOutParam interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
        InterfaceOutParam interfaceOutParamOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(interfaceOutParamDO), InterfaceOutParam.class);
        return ResultFactory.successOf(interfaceOutParamOne);
    }

    /**
     * describe 查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<List<InterfaceOutParam>> findList(InterfaceOutParam interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
        List<InterfaceOutParam> interfaceOutParamList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(interfaceOutParamDO), InterfaceOutParam.class);
        return ResultFactory.successOf(interfaceOutParamList);
    }

    /**
     * describe 分页查询多个接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<LazyPage<InterfaceOutParam>> findPage(int size, int current, InterfaceOutParam interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<InterfaceOutParam> interfaceOutParamLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(interfaceOutParamDO), lazyPage, InterfaceOutParam.class);
        return ResultFactory.successOf(interfaceOutParamLazyPage);
    }

    /**
     * describe 删除接口输出参数
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/

    @Override
    public Result<InterfaceOutParam> remove(InterfaceOutParam interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
        //  lazyLambdaStream.delete(interfaceOutParamDO);
        return ResultFactory.successOf();
    }

}