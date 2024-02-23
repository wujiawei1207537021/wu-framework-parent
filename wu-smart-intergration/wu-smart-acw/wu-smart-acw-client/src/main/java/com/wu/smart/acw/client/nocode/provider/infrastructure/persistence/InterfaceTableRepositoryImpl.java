package com.wu.smart.acw.client.nocode.provider.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceTableConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceTableDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 接口与表的关联
 *
 * @author Jia wei Wu
 * @date 2023/08/15 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Repository
public class InterfaceTableRepositoryImpl implements InterfaceTableRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<InterfaceTable> story(InterfaceTable interfaceTable) {
        InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
        lazyLambdaStream.upsert(interfaceTableDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<InterfaceTable> batchStory(List<InterfaceTable> interfaceTableList) {
        List<InterfaceTableDO> interfaceTableDOList = interfaceTableList.stream().map(InterfaceTableConverter::fromInterfaceTable).collect(Collectors.toList());
        lazyLambdaStream.upsert(interfaceTableDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<InterfaceTable> findOne(InterfaceTable interfaceTable) {
        InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
        InterfaceTable interfaceTableOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(interfaceTableDO), InterfaceTable.class);
        return ResultFactory.successOf(interfaceTableOne);
    }

    /**
     * describe 查询多个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<List<InterfaceTable>> findList(InterfaceTable interfaceTable) {
        InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
        List<InterfaceTable> interfaceTableList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(interfaceTableDO), InterfaceTable.class);
        return ResultFactory.successOf(interfaceTableList);
    }

    /**
     * describe 分页查询多个接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<LazyPage<InterfaceTable>> findPage(int size, int current, InterfaceTable interfaceTable) {
        InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<InterfaceTable> interfaceTableLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(interfaceTableDO), lazyPage, InterfaceTable.class);
        return ResultFactory.successOf(interfaceTableLazyPage);
    }

    /**
     * describe 删除接口与表的关联
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/15 01:04 下午
     **/

    @Override
    public Result<InterfaceTable> remove(InterfaceTable interfaceTable) {
        InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
        //  lazyLambdaStream.delete(interfaceTableDO);
        return ResultFactory.successOf();
    }

}