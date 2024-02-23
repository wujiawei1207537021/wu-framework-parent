package com.wu.smart.acw.server.domain.model.model.acw.redis.instance;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe Redis服务器 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwRedisInstanceRepository {


    /**
     * describe 新增Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwRedisInstance> story(AcwRedisInstance acwRedisInstance);

    /**
     * describe 批量新增Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwRedisInstance> batchStory(List<AcwRedisInstance> acwRedisInstanceList);

    /**
     * describe 查询单个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwRedisInstance> findOne(AcwRedisInstance acwRedisInstance);

    /**
     * describe 查询多个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwRedisInstance>> findList(AcwRedisInstance acwRedisInstance);

    /**
     * describe 分页查询多个Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwRedisInstance>> findPage(int size, int current, AcwRedisInstance acwRedisInstance);

    /**
     * describe 删除Redis服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwRedisInstance> remove(AcwRedisInstance acwRedisInstance);

}