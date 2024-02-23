package com.wu.smart.acw.server.domain.model.model.acw.dependency;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 依赖 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwDependencyRepository {


    /**
     * describe 新增依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwDependency> story(AcwDependency acwDependency);

    /**
     * describe 批量新增依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwDependency> batchStory(List<AcwDependency> acwDependencyList);

    /**
     * describe 查询单个依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwDependency> findOne(AcwDependency acwDependency);

    /**
     * describe 查询多个依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwDependency>> findList(AcwDependency acwDependency);

    /**
     * describe 分页查询多个依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwDependency>> findPage(int size, int current, AcwDependency acwDependency);

    /**
     * describe 删除依赖
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwDependency> remove(AcwDependency acwDependency);

}