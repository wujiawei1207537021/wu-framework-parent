package com.wu.smart.acw.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.acw.redis.instance.*;
import com.wu.smart.acw.server.application.dto.AcwRedisInstanceDTO;
import com.wu.smart.acw.server.domain.model.model.acw.redis.instance.AcwRedisInstance;

import java.util.List;

/**
 * describe Redis服务器
 *
 * @author Jia wei Wu
 * @date 2023/07/26 04:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface AcwRedisInstanceApplication {


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

    Result<AcwRedisInstance> story(AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand);

    /**
     * describe 更新Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    Result<AcwRedisInstance> updateOne(AcwRedisInstanceUpdateCommand acwRedisInstanceUpdateCommand);

    /**
     * describe 查询单个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/07/26 04:09 下午
     **/

    Result<AcwRedisInstance> findOne(AcwRedisInstanceQueryOneCommand acwRedisInstanceQueryOneCommand);

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

    Result<List<AcwRedisInstanceDTO>> findList(AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand);

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

    Result<LazyPage<AcwRedisInstanceDTO>> findPage(int size, int current, AcwRedisInstanceQueryListCommand acwRedisInstanceQueryListCommand);

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

    Result<AcwRedisInstance> remove(AcwRedisInstanceRemoveCommand acwRedisInstanceRemoveCommand);

    /**
     * 测试链接
     * @param acwRedisInstanceStoryCommand 链接信息
     * @return void
     */
    Result<Void> test(AcwRedisInstanceStoryCommand acwRedisInstanceStoryCommand);
}