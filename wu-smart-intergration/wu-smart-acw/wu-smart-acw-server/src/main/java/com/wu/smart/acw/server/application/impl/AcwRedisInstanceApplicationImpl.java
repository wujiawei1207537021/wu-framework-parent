package com.wu.smart.acw.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.smart.acw.server.application.AcwRedisInstanceApplication;
import com.wu.smart.acw.server.application.assembler.AcwRedisInstanceDTOAssembler;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisInstanceDTO;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstanceRepository;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AcwRedisInstanceApplicationImpl implements AcwRedisInstanceApplication {

    @Autowired
    AcwRedisInstanceRepository acwRedisInstanceRepository;

    /**
     * describe 新增Redis服务器
     *
     * @param
     * @param acwRedisInstanceStoryCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> story(AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand) {
        acwRedisInstanceStoryCommand.setIsDeleted(false);
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceStoryCommand);
        // 创建redis链接
        return acwRedisInstanceRepository.story(acwRedisInstance);
    }

    /**
     * describe 更新Redis服务器
     *
     * @param
     * @param acwRedisInstanceUpdateCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> updateOne(AcwRedisInstanceUpdateCommand acwRedisInstanceUpdateCommand) {
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceUpdateCommand);
        return acwRedisInstanceRepository.story(acwRedisInstance);
    }

    /**
     * describe 查询单个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryOneCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> findOne(AcwRedisInstanceQueryOneCommand acwRedisInstanceQueryOneCommand) {
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceQueryOneCommand);
        return acwRedisInstanceRepository.findOne(acwRedisInstance);
    }

    /**
     * describe 查询多个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryListCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<List<AcwRedisInstanceDTO>> findList(AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand) {
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceQueryListCommand);
        return acwRedisInstanceRepository.findList(acwRedisInstance).convert(acwRedisInstances -> acwRedisInstances.stream().map(AcwRedisInstanceDTOAssembler.INSTANCE::fromAcwRedisInstance).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个Redis服务器
     *
     * @param
     * @param acwRedisInstanceQueryListCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<LazyPage<AcwRedisInstanceDTO>> findPage(int size, int current, AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand) {
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceQueryListCommand);
        Result<LazyPage<AcwRedisInstance>> page = acwRedisInstanceRepository.findPage(size, current, acwRedisInstance);
        return page.convert(acwRedisInstanceLazyPage -> acwRedisInstanceLazyPage.convert(AcwRedisInstanceDTOAssembler.INSTANCE::fromAcwRedisInstance));
    }

    /**
     * describe 删除Redis服务器
     *
     * @param
     * @param acwRedisInstanceRemoveCommand
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    @Override
    public Result<AcwRedisInstance> remove(AcwRedisInstanceRemoveCommand acwRedisInstanceRemoveCommand) {
        AcwRedisInstance acwRedisInstance = AcwRedisInstanceDTOAssembler.INSTANCE.toAcwRedisInstance(acwRedisInstanceRemoveCommand);
        return acwRedisInstanceRepository.remove(acwRedisInstance);
    }

    /**
     * 测试链接
     *
     * @param acwRedisInstanceStoryCommand 链接信息
     * @return void
     */
    @Override
    public Result<Void> test(AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand) {
        try {
            String host = acwRedisInstanceStoryCommand.getHost();
            Integer port = acwRedisInstanceStoryCommand.getPort();
            String password = acwRedisInstanceStoryCommand.getPassword();
            RedisURI.Builder builder = RedisURI.builder()                // <1> 创建单机连接的连接信息
                    .withHost(host)
                    .withPort(port)
                    .withTimeout(Duration.of(10, ChronoUnit.SECONDS));
            if (!ObjectUtils.isEmpty(password)) {
                builder
                        .withPassword(password);
            }
            RedisURI redisURI = builder.build();
            RedisClient redisClient = RedisClient.create(redisURI);
            StatefulRedisConnection<String, String> connect = redisClient.connect();
            RedisCommands<String, String> sync = connect.sync();
            String ping = sync.ping();
            System.out.println(ping);
            connect.close();
            redisClient.shutdown();
            return ResultFactory.successOf();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.of(DefaultResultCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
        }
    }
}