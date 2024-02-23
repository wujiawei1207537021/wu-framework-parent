package com.wu.smart.acw.server.domain.model.model.application.api.table;

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

public interface ApplicationApiTableRepository {


    /**
     * describe 新增api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiTable> story(ApplicationApiTable applicationApiTable);

    /**
     * describe 批量新增api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiTable> batchStory(List<ApplicationApiTable> applicationApiTableList);

    /**
     * describe 查询单个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiTable> findOne(ApplicationApiTable applicationApiTable);

    /**
     * describe 查询多个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<List<ApplicationApiTable>> findList(ApplicationApiTable applicationApiTable);

    /**
     * describe 分页查询多个api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<LazyPage<ApplicationApiTable>> findPage(int size, int current, ApplicationApiTable applicationApiTable);

    /**
     * describe 删除api与表的关联关系
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/09/29 12:30 下午
     **/

    Result<ApplicationApiTable> remove(ApplicationApiTable applicationApiTable);

}