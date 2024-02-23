package com.wu.smart.acw.client.nocode.provider.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceInParamConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceInfoConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceOutParamConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceTableConverter;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceInParamDO;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceInfoDO;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceOutParamDO;
import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceTableDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfoRepository;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;
import com.wu.smart.acw.client.nocode.provider.model.interface_.table.InterfaceTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * describe Dataway 中的API
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@ConditionalOnBean(LazyLambdaStream.class)
@Repository
public class InterfaceInfoRepositoryImpl implements InterfaceInfoRepository {

    @Autowired
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<InterfaceInfo> story(InterfaceInfo interfaceInfo) {
        // 存储api信息

        InterfaceInfoDO interfaceInfoDO = InterfaceInfoConverter.INSTANCE.fromInterfaceInfo(interfaceInfo);
        interfaceInfoDO.setIsDeleted(false);
        interfaceInfoDO.setCreateTime(LocalDateTime.now());
        interfaceInfoDO.setUpdateTime(LocalDateTime.now());

        lazyLambdaStream.upsert(interfaceInfoDO);
        // 根据接口路径、接口方式获取接口id
        Integer apiId = lazyLambdaStream.selectOne(LazyWrappers.<InterfaceInfoDO>lambdaWrapper()
                .eq(InterfaceInfoDO::getApiPath, interfaceInfo.getApiPath())
                .eq(InterfaceInfoDO::getIsDeleted, false)
                .eq(InterfaceInfoDO::getApiMethod, interfaceInfo.getApiMethod())
                .onlyUseAs()
                .as(InterfaceInfoDO::getApiId, InterfaceInfoDO::getApiId), Integer.class
        );
        // 存储 api关联表
        List<InterfaceTableDO> interfaceTableDOList = interfaceInfo.getInterfaceTableList()
                .stream()
                .map(interfaceTable -> {
                    InterfaceTableDO interfaceTableDO = InterfaceTableConverter.fromInterfaceTable(interfaceTable);
                    interfaceTableDO.setApiId(apiId);
                    interfaceTableDO.setIsDeleted(false);
                    return interfaceTableDO;
                })
                .toList();
        // 删除old数据
        lazyLambdaStream.delete(LazyWrappers.<InterfaceTableDO>lambdaWrapper()
                .eq(InterfaceTableDO::getApiId, apiId)
        );
        lazyLambdaStream.upsert(interfaceTableDOList);
        if (!ObjectUtils.isEmpty(interfaceInfo.getInterfaceInParamList())) {
            // 存储api in参数
            List<InterfaceInParamDO> interfaceInParamDOList = interfaceInfo.getInterfaceInParamList()
                    .stream()
                    .map(interfaceInParam -> {
                        InterfaceInParamDO interfaceInParamDO = InterfaceInParamConverter.fromInterfaceInParam(interfaceInParam);
                        interfaceInParamDO.setApiId(apiId);
                        interfaceInParamDO.setIsDeleted(false);
                        return interfaceInParamDO;
                    })
                    .toList();
            lazyLambdaStream.delete(LazyWrappers.<InterfaceInParamDO>lambdaWrapper()
                    .eq(InterfaceInParamDO::getApiId, apiId)
            );
            lazyLambdaStream.upsert(interfaceInParamDOList);
        }

        if (!ObjectUtils.isEmpty(interfaceInfo.getInterfaceOutParamList())) {
            // 存储 api out参数
            List<InterfaceOutParamDO> interfaceOutParamDOList = interfaceInfo.getInterfaceOutParamList()
                    .stream()
                    .map(interfaceOutParam -> {
                        InterfaceOutParamDO interfaceOutParamDO = InterfaceOutParamConverter.fromInterfaceOutParam(interfaceOutParam);
                        interfaceOutParamDO.setApiId(apiId);
                        interfaceOutParamDO.setIsDeleted(false);
                        return interfaceOutParamDO;
                    })
                    .toList();
            lazyLambdaStream.delete(LazyWrappers.<InterfaceOutParamDO>lambdaWrapper()
                    .eq(InterfaceOutParamDO::getApiId, apiId)
            );
            lazyLambdaStream.upsert(interfaceOutParamDOList);
        }
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> batchStory(List<InterfaceInfo> interfaceInfoList) {
        List<InterfaceInfoDO> interfaceInfoDOList = interfaceInfoList.stream().map(InterfaceInfoConverter.INSTANCE::fromInterfaceInfo).collect(Collectors.toList());
        lazyLambdaStream.upsert(interfaceInfoDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> findOne(InterfaceInfo interfaceInfo) {
        InterfaceInfoDO interfaceInfoDO = InterfaceInfoConverter.INSTANCE.fromInterfaceInfo(interfaceInfo);
        InterfaceInfo interfaceInfoOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(interfaceInfoDO), InterfaceInfo.class);
        if (!Objects.isNull(interfaceInfoOne)) {
            // 查询api 表
            List<InterfaceTable> interfaceTableList = lazyLambdaStream.selectList(LazyWrappers.<InterfaceTableDO>lambdaWrapper()
                    .eq(InterfaceTableDO::getIsDeleted, false)
                    .eq(InterfaceTableDO::getApiId, interfaceInfoOne.getApiId()), InterfaceTable.class
            );
            interfaceInfoOne.setInterfaceTableList(interfaceTableList);
            // 查询api 入惨
            List<InterfaceInParam> interfaceInParamList = lazyLambdaStream.selectList(LazyWrappers.<InterfaceInParamDO>lambdaWrapper()
                    .eq(InterfaceInParamDO::getIsDeleted, false)
                    .eq(InterfaceInParamDO::getApiId, interfaceInfoOne.getApiId()), InterfaceInParam.class
            );
            interfaceInfoOne.setInterfaceInParamList(interfaceInParamList);
            // 查询api 出参
            List<InterfaceOutParam> interfaceOutParamList = lazyLambdaStream.selectList(LazyWrappers.<InterfaceOutParamDO>lambdaWrapper()
                    .eq(InterfaceOutParamDO::getIsDeleted, false)
                    .eq(InterfaceOutParamDO::getApiId, interfaceInfoOne.getApiId()), InterfaceOutParam.class
            );
            interfaceInfoOne.setInterfaceOutParamList(interfaceOutParamList);
        }
        return ResultFactory.successOf(interfaceInfoOne);
    }

    /**
     * describe 查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<List<InterfaceInfo>> findList(InterfaceInfo interfaceInfo) {
        InterfaceInfoDO interfaceInfoDO = InterfaceInfoConverter.INSTANCE.fromInterfaceInfo(interfaceInfo);
        List<InterfaceInfo> interfaceInfoList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(interfaceInfoDO), InterfaceInfo.class);
        return ResultFactory.successOf(interfaceInfoList);
    }

    /**
     * describe 分页查询多个Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<LazyPage<InterfaceInfo>> findPage(int size, int current, InterfaceInfo interfaceInfo) {
        InterfaceInfoDO interfaceInfoDO = InterfaceInfoConverter.INSTANCE.fromInterfaceInfo(interfaceInfo);
        LazyPage lazyPage = new LazyPage(current, size);
        LazyPage<InterfaceInfo> interfaceInfoLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(interfaceInfoDO), lazyPage, InterfaceInfo.class);
        return ResultFactory.successOf(interfaceInfoLazyPage);
    }

    /**
     * describe 删除Dataway 中的API
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/

    @Override
    public Result<InterfaceInfo> remove(InterfaceInfo interfaceInfo) {
        InterfaceInfoDO interfaceInfoDO = InterfaceInfoConverter.INSTANCE.fromInterfaceInfo(interfaceInfo);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(interfaceInfoDO));
        return ResultFactory.successOf();
    }

    /**
     * 根据api路径获取api信息
     *
     * @param apiPath
     * @return
     */
    @Override
    public InterfaceInfo findOneByApiPath(String apiPath, String apiMethod) {
        return lazyLambdaStream.selectOne(
                LazyWrappers
                        .<InterfaceInfoDO>lambdaWrapper()
                        .eq(InterfaceInfoDO::getApiPath, apiPath)
                        .eq(InterfaceInfoDO::getIsDeleted, false)
                        .eq(InterfaceInfoDO::getApiMethod, apiMethod),
                InterfaceInfo.class
        );
    }
}