package com.wu.smart.acw.server.domain.model.model.acw.application.api.table;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe api与表的关联关系 
 *
 * @author Jia wei Wu
 * @date 2023/09/29 12:30 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AcwApplicationApiTableRepository {


    /**
     * describe 新增api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiTable> story(AcwApplicationApiTable acwApplicationApiTable);

    /**
     * describe 批量新增api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiTable> batchStory(List<AcwApplicationApiTable> acwApplicationApiTableList);

    /**
     * describe 查询单个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiTable> findOne(AcwApplicationApiTable acwApplicationApiTable);

    /**
     * describe 查询多个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<AcwApplicationApiTable>> findList(AcwApplicationApiTable acwApplicationApiTable);

    /**
     * describe 分页查询多个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<AcwApplicationApiTable>> findPage(int size, int current, AcwApplicationApiTable acwApplicationApiTable);

    /**
     * describe 删除api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<AcwApplicationApiTable> remove(AcwApplicationApiTable acwApplicationApiTable);

}