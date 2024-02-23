package com.wu.smart.acw.server.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.OrmArchitecture;
import com.wu.framework.inner.lazy.config.enums.WebArchitecture;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.exceptions.RuntimeExceptionFactory;
import com.wu.smart.acw.core.client.api.command.AcwClientGenJavaAPICommand;
import com.wu.smart.acw.core.constant.AcwNettyMessageType;
import com.wu.smart.acw.server.application.AcwClientJavaPathApplication;
import com.wu.smart.acw.server.application.assembler.AcwClientJavaPathDTOAssembler;
import com.wu.smart.acw.server.application.command.acw.client.java.path.*;
import com.wu.smart.acw.server.application.dto.AcwClientJavaPathDTO;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstanceRepository;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPathRepository;
import com.wu.smart.acw.server.domain.model.model.acw.instance.AcwInstanceRepository;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * describe 客户端使用创建Java代码常用路径
 *
 * @author Jia wei Wu
 * @date 2023/12/08 04:36 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AcwClientJavaPathApplicationImpl implements AcwClientJavaPathApplication {

    @Resource
    AcwClientJavaPathRepository acwClientJavaPathRepository;
    @Resource
    AcwClientInstanceRepository acwClientInstanceRepository;
    @Resource
    AcwInstanceRepository acwInstanceRepository;
    @Resource
    LazyLambdaStream lazyLambdaStream;
    @Resource
    LazyOperationConfig lazyOperationConfig;

    /**
     * describe 新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommand 新增客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<AcwClientJavaPath> story(AcwClientJavaPathStoryCommand acwClientJavaPathStoryCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathStoryCommand);
        return acwClientJavaPathRepository.story(acwClientJavaPath);
    }

    /**
     * describe 批量新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommandList 批量新增客户端使用创建Java代码常用路径
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<List<AcwClientJavaPath>> batchStory(List<AcwClientJavaPathStoryCommand> acwClientJavaPathStoryCommandList) {
        List<AcwClientJavaPath> acwClientJavaPathList = acwClientJavaPathStoryCommandList.stream().map(AcwClientJavaPathDTOAssembler.INSTANCE::toAcwClientJavaPath).collect(Collectors.toList());
        return acwClientJavaPathRepository.batchStory(acwClientJavaPathList);
    }

    /**
     * describe 更新客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathUpdateCommand 更新客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径领域对象
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<AcwClientJavaPath> updateOne(AcwClientJavaPathUpdateCommand acwClientJavaPathUpdateCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathUpdateCommand);
        return acwClientJavaPathRepository.story(acwClientJavaPath);
    }

    /**
     * describe 查询单个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryOneCommand 查询单个客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPathDTO>} 客户端使用创建Java代码常用路径DTO对象
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<AcwClientJavaPathDTO> findOne(AcwClientJavaPathQueryOneCommand acwClientJavaPathQueryOneCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathQueryOneCommand);
        return acwClientJavaPathRepository.findOne(acwClientJavaPath).convert(AcwClientJavaPathDTOAssembler.INSTANCE::fromAcwClientJavaPath);
    }

    /**
     * describe 查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 查询多个客户端使用创建Java代码常用路径
     * @return {@link Result<List<AcwClientJavaPathDTO>>} 客户端使用创建Java代码常用路径DTO对象
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<List<AcwClientJavaPathDTO>> findList(AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathQueryListCommand);
        return acwClientJavaPathRepository.findList(acwClientJavaPath).convert(acwClientJavaPaths -> acwClientJavaPaths.stream().map(AcwClientJavaPathDTOAssembler.INSTANCE::fromAcwClientJavaPath).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 分页查询多个客户端使用创建Java代码常用路径
     * @return {@link Result<LazyPage<AcwClientJavaPathDTO>>} 分页客户端使用创建Java代码常用路径DTO对象
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<LazyPage<AcwClientJavaPathDTO>> findPage(int size, int current, AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathQueryListCommand);
        return acwClientJavaPathRepository.findPage(size, current, acwClientJavaPath).convert(page -> page.convert(AcwClientJavaPathDTOAssembler.INSTANCE::fromAcwClientJavaPath));
    }

    /**
     * describe 删除客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathRemoveCommand 删除客户端使用创建Java代码常用路径
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径
     * @author Jia wei Wu
     * @date 2023/12/08 04:36 下午
     **/

    @Override
    public Result<AcwClientJavaPath> remove(AcwClientJavaPathRemoveCommand acwClientJavaPathRemoveCommand) {
        AcwClientJavaPath acwClientJavaPath = AcwClientJavaPathDTOAssembler.INSTANCE.toAcwClientJavaPath(acwClientJavaPathRemoveCommand);
        return acwClientJavaPathRepository.remove(acwClientJavaPath);
    }

    /**
     * 根据表生成本地Java对应代码
     *
     * @param acwClientGenerateLocalJavaCommand 参数
     * @return Result
     */
    @Override
    public Result<?> clientGenerateLocalJava(AcwClientGenerateLocalJavaCommand acwClientGenerateLocalJavaCommand) {
//        根据客户端ID查询客户端信息
        String clientId = acwClientGenerateLocalJavaCommand.getClientId();
        String instanceId = acwClientGenerateLocalJavaCommand.getInstanceId();
        List<String> tableList = acwClientGenerateLocalJavaCommand.getTableList();
        String schemaName = acwClientGenerateLocalJavaCommand.getSchemaName();
        String absolutePath = acwClientGenerateLocalJavaCommand.getAbsolutePath();
        String packageName = acwClientGenerateLocalJavaCommand.getPackageName();
        String prefix = acwClientGenerateLocalJavaCommand.getPrefix();// 文件前缀
        WebArchitecture webArchitecture = acwClientGenerateLocalJavaCommand.getWebArchitecture();
        OrmArchitecture ormArchitecture = acwClientGenerateLocalJavaCommand.getOrmArchitecture();
        // 保存或者更新客户端使用的地址
        AcwClientJavaPathStoryCommand acwClientJavaPathStoryCommand = new AcwClientJavaPathStoryCommand();
        acwClientJavaPathStoryCommand.setAbsolutePath(absolutePath);
        acwClientJavaPathStoryCommand.setClientId(clientId);
        acwClientJavaPathStoryCommand.setSchemaName(schemaName);
        acwClientJavaPathStoryCommand.setPackageName(packageName);
        acwClientJavaPathStoryCommand.setInstanceId(instanceId);

        story(acwClientJavaPathStoryCommand);
        AcwClientInstance acwClientInstance = new AcwClientInstance();
        acwClientInstance.setClientId(clientId);
        // 获取客户端ID对应的通道
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId);
//        AcwClientApi acwClientApi = acwClientInstanceRepository.findOne(acwClientInstance).applyOther(acwClientInstanceFindOne -> {
//            String ip = acwClientInstanceFindOne.getIp();
//            Integer getPort = acwClientInstanceFindOne.getPort();
//            return WebClientFactory.acwWebClientProxyApi(String.format("http://%s:%s", ip, getPort), AcwClientApi.class);
//        });
        if (clientChannel == null) {
            RuntimeExceptionFactory.of("不存在的客户端:" + clientId);
        }

        // 切换数据源
        acwInstanceRepository.switchInstance(instanceId);
        LazyOperationConfig.ReverseEngineering reverseEngineering = lazyOperationConfig.getReverseEngineering();
        if (!ObjectUtils.isEmpty(absolutePath)) {
            reverseEngineering.setResourceFilePrefix(absolutePath + File.separator);
        }
        if (!ObjectUtils.isEmpty(packageName)) {
            reverseEngineering.setPackageName(packageName);
        }
        if (!ObjectUtils.isEmpty(webArchitecture)) {
            reverseEngineering.setWebArchitecture(webArchitecture);
        }
        if (!ObjectUtils.isEmpty(ormArchitecture)) {
            reverseEngineering.setOrmArchitecture(ormArchitecture);
        }
        try {
            // 查询表信息

            Map<String, LazyTableInfo> tableInfoMap = lazyLambdaStream
                    .selectList(
                            LazyWrappers.<LazyTableInfo>lambdaWrapper()
                                    .eq(LazyTableInfo::getTableSchema, schemaName)
                                    .in(LazyTableInfo::getTableName, tableList))
                    .stream()
                    .collect(Collectors.toMap(LazyTableInfo::getTableName, Function.identity(), (A, B) -> A));

            // 查询表字段信息
            Map<String/*表*/, List<LazyColumn>/*表字段*/> tableColumnMap = lazyLambdaStream.selectList(
                            LazyWrappers.<LazyColumn>lambdaWrapper()
                                    .eq(LazyColumn::getTableSchema, schemaName)
                                    .in(LazyColumn::getTableName, tableList))
                    .stream().collect(Collectors.groupingBy(LazyColumn::getTableName));

            // 生成数据
            List<AcwClientGenJavaAPICommand> acwClientGenJavaAPICommands = tableColumnMap.entrySet().stream().map(stringListEntry -> {
                String tableName = stringListEntry.getKey();
                List<LazyColumn> lazyColumnList = stringListEntry.getValue();
                LazyTableInfo lazyTableInfo = tableInfoMap.get(tableName);
                List<AcwClientGenJavaAPICommand.InnerLazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = lazyColumnList.stream().map(lazyColumn -> {
                    AcwClientGenJavaAPICommand.InnerLazyTableFieldEndpoint fieldEndpoint = new AcwClientGenJavaAPICommand.InnerLazyTableFieldEndpoint();
                    String columnName = lazyColumn.getColumnName();
                    fieldEndpoint.setColumnName(columnName);
                    fieldEndpoint.setName(CamelAndUnderLineConverter.lineToHumpField(columnName));
                    fieldEndpoint.setComment(lazyColumn.getColumnComment());
                    fieldEndpoint.setColumnType(lazyColumn.getColumnType());
                    fieldEndpoint.setDataType(lazyColumn.getDataType());
                    fieldEndpoint.setNotNull(NormalUsedString.NO.equalsIgnoreCase(lazyColumn.getIsNullable()));
                    fieldEndpoint.setDefaultValue(lazyColumn.getColumnDefault());
                    fieldEndpoint.setAlias(columnName);
                    String extra = lazyColumn.getExtra();
                    fieldEndpoint.setExtra(extra);
                    return fieldEndpoint;
                }).collect(Collectors.toList());
                AcwClientGenJavaAPICommand.InnerReverseClassLazyTableEndpoint reverseClassLazyTableEndpoint = new AcwClientGenJavaAPICommand.InnerReverseClassLazyTableEndpoint();
                reverseClassLazyTableEndpoint.setSchema(schemaName);
                reverseClassLazyTableEndpoint.setTableName(tableName);
                if (ObjectUtils.isEmpty(prefix)) {
                    reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.lineToHumpClass(tableName));
                } else {
                    reverseClassLazyTableEndpoint.setClassName(CamelAndUnderLineConverter.capitalizeFirstLetter(prefix) + CamelAndUnderLineConverter.lineToHumpClass(tableName));
                }
                reverseClassLazyTableEndpoint.setInLazyTableFieldEndpoints(fieldLazyTableFieldEndpointList);
                // 数据拷贝 否则数据转换有问题
                List<AcwClientGenJavaAPICommand.InnerLazyTableFieldEndpoint> outLazyTableFieldEndpoints =
                        fieldLazyTableFieldEndpointList.stream().map(AcwClientGenJavaAPICommand.InnerLazyTableFieldEndpoint::copy)
                                .toList();
                reverseClassLazyTableEndpoint.setOutLazyTableFieldEndpoints(outLazyTableFieldEndpoints);
                reverseClassLazyTableEndpoint.setComment(lazyTableInfo.getTableComment());
                reverseClassLazyTableEndpoint.setPackageName(reverseEngineering.getPackageName());

                String entitySuffix = reverseEngineering.getEntitySuffix();
                if (!ObjectUtils.isEmpty(entitySuffix)) {
                    String className = reverseClassLazyTableEndpoint.getClassName();
                    reverseClassLazyTableEndpoint.setClassName(className + entitySuffix);
                }

                AcwClientGenJavaAPICommand acwClientGenJavaAPICommand = new AcwClientGenJavaAPICommand();

                acwClientGenJavaAPICommand.setReverseEngineering(DataTransformUntil.copyBean(reverseEngineering));
                acwClientGenJavaAPICommand.setInnerReverseClassLazyTableEndpoint(reverseClassLazyTableEndpoint);
                return acwClientGenJavaAPICommand;
            }).toList();
//            return acwClientApi.generateLocalJava(acwClientGenJavaAPICommands);
            Channel channel = clientChannel.getChannel();
            NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
            nettyProxyMsg.setType(AcwNettyMessageType.GENERATE_LOCAL_JAVA);
            nettyProxyMsg.setData(JSONObject.toJSONString(acwClientGenJavaAPICommands).getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(nettyProxyMsg);
            return ResultFactory.successOf();

        } catch (Exception e) {
            throw e;
        } finally {
            DynamicLazyDSContextHolder.clear();
        }

    }
}