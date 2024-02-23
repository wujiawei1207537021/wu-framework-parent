package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisInstanceDTO;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/10/24 01:04 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface AcwRedisInstanceDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstanceDTOAssembler INSTANCE = Mappers.getMapper(AcwRedisInstanceDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceUpdateCommand acwRedisInstanceUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceQueryOneCommand acwRedisInstanceQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstance toAcwRedisInstance(AcwRedisInstanceRemoveCommand acwRedisInstanceRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/10/24 01:04 下午
     **/
    AcwRedisInstanceDTO fromAcwRedisInstance(AcwRedisInstance acwRedisInstance);
}