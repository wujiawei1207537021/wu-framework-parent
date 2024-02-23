package com.wu.framework.automation.platform.server.starter.domain.model.automation;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AutomationRepository {


    /**
     * describe 新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> story(Automation automation);

    /**
     * describe 批量新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<List<Automation>> batchStory(List<Automation> automationList);

    /**
     * describe 查询单个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> findOne(Automation automation);

    /**
     * describe 查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<List<Automation>> findList(Automation automation);

    /**
     * describe 分页查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<LazyPage<Automation>> findPage(int size,int current,Automation automation);

    /**
     * describe 删除自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> remove(Automation automation);

}