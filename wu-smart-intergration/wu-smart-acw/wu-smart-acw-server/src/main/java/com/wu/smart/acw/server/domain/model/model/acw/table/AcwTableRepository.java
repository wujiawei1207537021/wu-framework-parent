package com.wu.smart.acw.server.domain.model.model.acw.table;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 数据库表信息（即将弃用） 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwTableRepository {


    /**
     * describe 新增数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTable> story(AcwTable acwTable);

    /**
     * describe 批量新增数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTable> batchStory(List<AcwTable> acwTableList);

    /**
     * describe 查询单个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTable> findOne(AcwTable acwTable);

    /**
     * describe 查询多个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwTable>> findList(AcwTable acwTable);

    /**
     * describe 分页查询多个数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwTable>> findPage(int size, int current, AcwTable acwTable);

    /**
     * describe 删除数据库表信息（即将弃用）
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwTable> remove(AcwTable acwTable);

}