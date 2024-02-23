package com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 自动化http节点动作 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AutomationNodeHttpActionRepository {


    /**
     * describe 新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> story(AutomationNodeHttpAction automationNodeHttpAction);

    /**
     * describe 批量新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<List<AutomationNodeHttpAction>> batchStory(List<AutomationNodeHttpAction> automationNodeHttpActionList);

    /**
     * describe 查询单个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> findOne(AutomationNodeHttpAction automationNodeHttpAction);

    /**
     * describe 查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<List<AutomationNodeHttpAction>> findList(AutomationNodeHttpAction automationNodeHttpAction);

    /**
     * describe 分页查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<LazyPage<AutomationNodeHttpAction>> findPage(int size,int current,AutomationNodeHttpAction automationNodeHttpAction);

    /**
     * describe 删除自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> remove(AutomationNodeHttpAction automationNodeHttpAction);

}