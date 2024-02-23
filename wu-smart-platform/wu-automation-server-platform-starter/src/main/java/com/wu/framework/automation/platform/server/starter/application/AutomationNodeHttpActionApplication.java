package com.wu.framework.automation.platform.server.starter.application;

import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeHttpActionDTO;
import com.wu.framework.response.Result;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryOneCommand;

import java.util.List;
import java.util.Map;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化http节点动作 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AutomationNodeHttpActionApplication {


    /**
     * describe 新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> story(AutomationNodeHttpActionStoryCommand automationNodeHttpActionStoryCommand);

    /**
     * describe 更新自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> updateOne(AutomationNodeHttpActionUpdateCommand automationNodeHttpActionUpdateCommand);

    /**
     * describe 查询单个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpActionDTO> findOne(AutomationNodeHttpActionQueryOneCommand automationNodeHttpActionQueryOneCommand);

    /**
     * describe 查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result <List<AutomationNodeHttpActionDTO>> findList(AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand);

    /**
     * describe 分页查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result <LazyPage<AutomationNodeHttpActionDTO>> findPage(int size,int current,AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand);

    /**
     * describe 删除自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    Result<AutomationNodeHttpAction> remove(AutomationNodeHttpActionRemoveCommand automationNodeHttpActionRemoveCommand);

    /**
     * 执行动作
     * @param actionId 动作ID
     * @return void
     */
    Result<Map<String,Object>> executeOne(String actionId);
}