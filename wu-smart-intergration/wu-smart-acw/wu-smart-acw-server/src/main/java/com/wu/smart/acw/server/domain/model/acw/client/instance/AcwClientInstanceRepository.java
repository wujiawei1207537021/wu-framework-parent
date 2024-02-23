package com.wu.smart.acw.server.domain.model.acw.client.instance;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwClientInstanceRepository {


    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstance 新增客户端实例
     * @return {@link  Result<AcwClientInstance>} 客户端实例新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> story(AcwClientInstance acwClientInstance);

    /**
     * describe 批量新增客户端实例
     *
     * @param acwClientInstanceList 批量新增客户端实例     
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<List<AcwClientInstance>> batchStory(List<AcwClientInstance> acwClientInstanceList);

    /**
     * describe 查询单个客户端实例
     *
     * @param acwClientInstance 查询单个客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> findOne(AcwClientInstance acwClientInstance);

    /**
     * describe 查询多个客户端实例
     *
     * @param acwClientInstance 查询多个客户端实例     
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<List<AcwClientInstance>> findList(AcwClientInstance acwClientInstance);

    /**
     * describe 分页查询多个客户端实例
     *
     * @param size 当前页数
     * @param current 当前页
     * @param acwClientInstance 分页查询多个客户端实例     
     * @return {@link Result< LazyPage <AcwClientInstance>>} 分页客户端实例领域对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<LazyPage<AcwClientInstance>> findPage(int size,int current,AcwClientInstance acwClientInstance);

    /**
     * describe 删除客户端实例
     *
     * @param acwClientInstance 删除客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> remove(AcwClientInstance acwClientInstance);

    /**
     * describe 是否存在客户端实例
     *
     * @param acwClientInstance 是否存在客户端实例     
     * @return {@link Result<Boolean>} 客户端实例是否存在     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<Boolean> exists(AcwClientInstance acwClientInstance);

}