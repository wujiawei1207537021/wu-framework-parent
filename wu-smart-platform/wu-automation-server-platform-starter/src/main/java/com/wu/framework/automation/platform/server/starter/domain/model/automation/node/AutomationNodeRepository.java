package com.wu.framework.automation.platform.server.starter.domain.model.automation.node;

import com.wu.framework.response.Result;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface AutomationNodeRepository {


    /**
     * describe 新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> story(AutomationNode automationNode);

    /**
     * describe 批量新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<List<AutomationNode>> batchStory(List<AutomationNode> automationNodeList);

    /**
     * describe 查询单个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> findOne(AutomationNode automationNode);

    /**
     * describe 查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<List<AutomationNode>> findList(AutomationNode automationNode);

    /**
     * describe 分页查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<LazyPage<AutomationNode>> findPage(int size,int current,AutomationNode automationNode);

    /**
     * describe 删除自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> remove(AutomationNode automationNode);

}