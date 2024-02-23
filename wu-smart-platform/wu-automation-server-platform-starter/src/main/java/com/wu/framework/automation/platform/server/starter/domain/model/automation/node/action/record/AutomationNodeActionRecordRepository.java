package com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AutomationNodeActionRecordRepository {


    /**
     * describe 新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> story(AutomationNodeActionRecord automationNodeActionRecord);

    /**
     * describe 批量新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<List<AutomationNodeActionRecord>> batchStory(List<AutomationNodeActionRecord> automationNodeActionRecordList);

    /**
     * describe 查询单个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> findOne(AutomationNodeActionRecord automationNodeActionRecord);

    /**
     * describe 查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<List<AutomationNodeActionRecord>> findList(AutomationNodeActionRecord automationNodeActionRecord);

    /**
     * describe 分页查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<LazyPage<AutomationNodeActionRecord>> findPage(int size,int current,AutomationNodeActionRecord automationNodeActionRecord);

    /**
     * describe 删除自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> remove(AutomationNodeActionRecord automationNodeActionRecord);

}