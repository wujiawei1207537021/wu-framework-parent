package com.wu.framework.automation.platform.server.starter.application.impl;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeHttpActionApplication;
import com.wu.framework.automation.platform.server.starter.application.assembler.AutomationNodeHttpActionDTOAssembler;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryOneCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionStoryCommand;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.automation.platform.server.starter.component.adapter.AutomationNodeActionAdapter;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeHttpActionDTO;

import java.util.Map;
import java.util.stream.Collectors;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpActionRepository;

import java.util.List;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化http节点动作
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AutomationNodeHttpActionApplicationImpl implements AutomationNodeHttpActionApplication {

    @Autowired
    AutomationNodeHttpActionRepository automationNodeHttpActionRepository;

    @Autowired
    AutomationNodeActionAdapter automationNodeActionAdapter;

    /**
     * describe 新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> story(AutomationNodeHttpActionStoryCommand automationNodeHttpActionStoryCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionStoryCommand);
        return automationNodeHttpActionRepository.story(automationNodeHttpAction);
    }

    /**
     * describe 更新自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> updateOne(AutomationNodeHttpActionUpdateCommand automationNodeHttpActionUpdateCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionUpdateCommand);
        return automationNodeHttpActionRepository.story(automationNodeHttpAction);
    }

    /**
     * describe 查询单个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpActionDTO> findOne(AutomationNodeHttpActionQueryOneCommand automationNodeHttpActionQueryOneCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionQueryOneCommand);
        return automationNodeHttpActionRepository.findOne(automationNodeHttpAction).convert(AutomationNodeHttpActionDTOAssembler.INSTANCE::fromAutomationNodeHttpAction);
    }

    /**
     * describe 查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<List<AutomationNodeHttpActionDTO>> findList(AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionQueryListCommand);
        return automationNodeHttpActionRepository.findList(automationNodeHttpAction).convert(automationNodeHttpActions -> automationNodeHttpActions.stream().map(AutomationNodeHttpActionDTOAssembler.INSTANCE::fromAutomationNodeHttpAction).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<LazyPage<AutomationNodeHttpActionDTO>> findPage(int size, int current, AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionQueryListCommand);
        return automationNodeHttpActionRepository.findPage(size, current, automationNodeHttpAction).convert(page -> page.convert(AutomationNodeHttpActionDTOAssembler.INSTANCE::fromAutomationNodeHttpAction));
    }

    /**
     * describe 删除自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Override
    public Result<AutomationNodeHttpAction> remove(AutomationNodeHttpActionRemoveCommand automationNodeHttpActionRemoveCommand) {
        AutomationNodeHttpAction automationNodeHttpAction = AutomationNodeHttpActionDTOAssembler.INSTANCE.toAutomationNodeHttpAction(automationNodeHttpActionRemoveCommand);
        return automationNodeHttpActionRepository.remove(automationNodeHttpAction);
    }

    /**
     * 执行动作
     *
     * @param actionId 动作ID
     * @return void
     */
    @Override
    public Result<Map<String,Object>>  executeOne(String actionId) {
        // 查询动作
        AutomationNodeHttpAction automationNodeHttpAction = new AutomationNodeHttpAction();
        automationNodeHttpAction.setId(actionId);
        Result<AutomationNodeHttpAction> one = automationNodeHttpActionRepository.findOne(automationNodeHttpAction);

        return one.apply(action -> automationNodeActionAdapter.execute(action));
    }
}