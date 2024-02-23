package com.wu.smart.acw.server.domain.model.model.acw.instance;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;

import java.util.List;

/**
 * describe 数据库服务器
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface AcwInstanceRepository {


    /**
     * describe 新增数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstance> story(AcwInstance acwInstance);

    /**
     * describe 批量新增数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstance> batchStory(List<AcwInstance> acwInstanceList);

    /**
     * describe 查询单个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstance> findOne(AcwInstance acwInstance);

    /**
     * describe 查询多个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwInstance>> findList(AcwInstance acwInstance);

    /**
     * describe 分页查询多个数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwInstance>> findPage(int size, int current, AcwInstance acwInstance);

    /**
     * describe 删除数据库服务器
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwInstance> remove(AcwInstance acwInstance);

    /**
     * 切换实例
     *
     * @param instanceId
     * @return
     */
    AcwInstanceUo switchInstance(String instanceId);

}