package com.wu.smart.acw.server.application.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.stereotype.LazyRepository;
import com.wu.framework.inner.redis.component.LazyRedisLettuce;
import com.wu.framework.inner.redis.config.LazyRedisProperties;
import com.wu.framework.inner.redis.dynamic.LazyDynamicRedisAdapter;
import com.wu.framework.inner.redis.dynamic.LazyDynamicRedisEndpoint;
import com.wu.framework.inner.redis.dynamic.toolkit.DynamicLazyRedisContextHolder;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.application.AcwRedisConsoleApplication;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisDatabaseDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyDTO;
import com.wu.smart.acw.server.application.dto.AcwRedisKeyValueDTO;
import com.wu.smart.acw.server.infrastructure.entity.AcwRedisInstanceDO;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LazyRepository
public class AcwRedisConsoleApplicationImpl implements AcwRedisConsoleApplication {

    private final LazyRedisLettuce lazyRedisLettuce;
    private final LazyLambdaStream lazyLambdaStream;
    private final LazyDynamicRedisAdapter lazyDynamicRedisAdapter;

    public AcwRedisConsoleApplicationImpl(LazyRedisLettuce lazyRedisLettuce, LazyLambdaStream lazyLambdaStream, LazyDynamicRedisAdapter lazyDynamicRedisAdapter) {
        this.lazyRedisLettuce = lazyRedisLettuce;
        this.lazyLambdaStream = lazyLambdaStream;
        this.lazyDynamicRedisAdapter = lazyDynamicRedisAdapter;
    }


    /**
     * 获取当前实例中的所有的key
     *
     * @param acwRedisInstanceQueryKeyCommand key 查询参数
     * @return key dto
     */
    @Override
    public Result<LazyPage<AcwRedisKeyDTO>> findKeys(AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand) {
        // 切换redis实例
        String instanceId = acwRedisInstanceQueryKeyCommand.getInstanceId();
        int database = acwRedisInstanceQueryKeyCommand.getDatabase();

        switchRedis(instanceId, database);
        // 切换database
        // 查询key

        RedisCommands<String, String> sync = lazyRedisLettuce.sync();
        List<String> keys = sync.keys("*");


        // 清除上下文切换数据
        DynamicLazyRedisContextHolder.clear();
        LazyPage<AcwRedisKeyDTO> objectLazyPage = new LazyPage<>();
        List<AcwRedisKeyDTO> acwRedisKeyDTOS = keys.stream().map(s -> {
            AcwRedisKeyDTO acwRedisKeyDTO = new AcwRedisKeyDTO();
            acwRedisKeyDTO.setDatabase(database);
            acwRedisKeyDTO.setInstanceId(instanceId);
            acwRedisKeyDTO.setKey(s);
            return acwRedisKeyDTO;
        }).collect(Collectors.toList());
        objectLazyPage.setRecord(acwRedisKeyDTOS);

        return ResultFactory.successOf(objectLazyPage);
    }

    /**
     * 获取当前实例中的所有的database
     *
     * @param acwRedisInstanceQueryDatabaseCommand 查询条件
     * @return 获取当前实例中的所有的database
     */
    @Override
    public Result<LazyPage<AcwRedisDatabaseDTO>> findDataBases(AcwRedisInstanceQueryDatabaseCommand acwRedisInstanceQueryDatabaseCommand) {
        List<AcwRedisDatabaseDTO> acwRedisDatabaseDTOList = new ArrayList<>();


        String instanceId = acwRedisInstanceQueryDatabaseCommand.getInstanceId();
        switchRedis(instanceId, null);
        RedisCommands<String, String> sync = lazyRedisLettuce.sync();

        for (int i = 0; i <= 15; i++) {
            AcwRedisDatabaseDTO acwRedisDatabaseDTO = new AcwRedisDatabaseDTO();
            acwRedisDatabaseDTO.setDatabase(i);
            acwRedisDatabaseDTO.setInstanceId(instanceId);
            sync.select(i);

            List<String> keys = sync.keys("*");
            acwRedisDatabaseDTO.setKeysNum(keys.size());
            acwRedisDatabaseDTOList.add(acwRedisDatabaseDTO);
        }
        return ResultFactory.successOf(LazyPage.of(1, 10, acwRedisDatabaseDTOList));
    }

    /**
     * 添加redis数据
     *
     * @param acwRedisInstanceSetKeyCommand redis 数据库
     * @return
     */
    @Override
    public Result<Void> setKey(AcwRedisInstanceSetKeyCommand acwRedisInstanceSetKeyCommand) {


        String instanceId = acwRedisInstanceSetKeyCommand.getInstanceId();
        int database = acwRedisInstanceSetKeyCommand.getDatabase();
        String key = acwRedisInstanceSetKeyCommand.getKey();
        String value = acwRedisInstanceSetKeyCommand.getValue();
        // 切换redis
        switchRedis(instanceId, database);


        RedisCommands<String, String> sync = lazyRedisLettuce.sync();
        sync.select(database);
        try {
            sync.set(key, value);
            System.out.println(sync.get(key));
            return ResultFactory.successOf();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf();
        } finally {
            DynamicLazyRedisContextHolder.clear();
        }
    }

    /**
     * 获取当前实例中的所有的key、value
     *
     * @param acwRedisInstanceQueryKeyCommand 参数
     * @return 结果
     */
    @Override
    public Result<LazyPage<AcwRedisKeyValueDTO>> findKeysValues(AcwRedisInstanceQueryKeyCommand acwRedisInstanceQueryKeyCommand) {
        String instanceId = acwRedisInstanceQueryKeyCommand.getInstanceId();
        int database = acwRedisInstanceQueryKeyCommand.getDatabase();
        // 切换redis
        switchRedis(instanceId, database);


        RedisCommands<String, String> sync = lazyRedisLettuce.sync();
        sync.select(database);
        try {

            // 查询所有的key
            List<String> keys = sync.keys("*");
            List<AcwRedisKeyValueDTO> redisKeyValueDTOList = keys.stream().map(key -> {
                AcwRedisKeyValueDTO acwRedisKeyValueDTO = new AcwRedisKeyValueDTO();
                String value = sync.get(key);
                acwRedisKeyValueDTO.setKey(key);
                acwRedisKeyValueDTO.setValue(value);
                acwRedisKeyValueDTO.setInstanceId(instanceId);
                acwRedisKeyValueDTO.setDatabase(database);
                return acwRedisKeyValueDTO;
            }).collect(Collectors.toList());

            return ResultFactory.successOf(LazyPage.of(1, 10, redisKeyValueDTOList));
            // 查询对应的value

        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e);
        } finally {
            DynamicLazyRedisContextHolder.clear();
        }
    }

    /**
     * 移除redis数据
     *
     * @param acwRedisInstanceRemoveKeyCommand 参数
     * @return 返回结果
     */
    @Override
    public Result<Void> removeKey(AcwRedisInstanceRemoveKeyCommand acwRedisInstanceRemoveKeyCommand) {
        String instanceId = acwRedisInstanceRemoveKeyCommand.getInstanceId();
        int database = acwRedisInstanceRemoveKeyCommand.getDatabase();
        String key = acwRedisInstanceRemoveKeyCommand.getKey();

        // 切换redis

        switchRedis(instanceId, database);


        RedisCommands<String, String> sync = lazyRedisLettuce.sync();
        sync.select(database);
        try {
            sync.del(key);
            return ResultFactory.successOf();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf();
        } finally {
            DynamicLazyRedisContextHolder.clear();
        }
    }

    /**
     * 获取key 的value
     *
     * @param acwRedisInstanceQueryKeyValuesCommand 参数
     * @return 返回结果
     */
    @Override
    public Result<LazyPage<AcwRedisKeyValueDTO>> findKeyValue(AcwRedisInstanceQueryKeyValuesCommand acwRedisInstanceQueryKeyValuesCommand) {
        String instanceId = acwRedisInstanceQueryKeyValuesCommand.getInstanceId();
        int database = acwRedisInstanceQueryKeyValuesCommand.getDatabase();
        String key = acwRedisInstanceQueryKeyValuesCommand.getKey();
        switchRedis(instanceId, database);


        RedisCommands<String, String> sync = lazyRedisLettuce.sync();
        sync.select(database);
        try {

            AcwRedisKeyValueDTO acwRedisKeyValueDTO = new AcwRedisKeyValueDTO();
            String type = sync.type(key);
            System.out.println("key: " + key + "类型: " + type);
            String value = sync.get(key);
            acwRedisKeyValueDTO.setKey(key);
            acwRedisKeyValueDTO.setValue(value);
            acwRedisKeyValueDTO.setInstanceId(instanceId);
            acwRedisKeyValueDTO.setDatabase(database);

            return ResultFactory.successOf(LazyPage.of(1, 10, List.of(acwRedisKeyValueDTO)));
            // 查询对应的value

        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.errorOf(e);
        } finally {
            DynamicLazyRedisContextHolder.clear();
        }
    }

    /**
     * 切换redis实例
     */
    protected void switchRedis(String instanceId, Integer database) {
        // 查询 实例ID
        AcwRedisInstanceDO acwRedisInstanceDO = lazyLambdaStream.selectOne(LazyWrappers.<AcwRedisInstanceDO>lambdaWrapper()
                .eq(AcwRedisInstanceDO::getIsDeleted, false)
                .eq(AcwRedisInstanceDO::getId, instanceId)
        );
        Assert.notNull(acwRedisInstanceDO, "redis实例不存在");
        LazyRedisProperties lazyRedisProperties = new LazyRedisProperties();
        lazyRedisProperties.setHost(acwRedisInstanceDO.getHost());
        lazyRedisProperties.setPort(acwRedisInstanceDO.getPort());
        lazyRedisProperties.setAlias(acwRedisInstanceDO.getId().toString());
        lazyRedisProperties.setPassword(acwRedisInstanceDO.getPassword());
        // 加载实例ID
        lazyDynamicRedisAdapter.loading(lazyRedisProperties);

        // 切换redis
        LazyDynamicRedisEndpoint lazyDynamicRedisEndpoint = new LazyDynamicRedisEndpoint();
        lazyDynamicRedisEndpoint.setName(instanceId);
        lazyDynamicRedisEndpoint.setDatabase(database);
        DynamicLazyRedisContextHolder.push(lazyDynamicRedisEndpoint);


    }
}
