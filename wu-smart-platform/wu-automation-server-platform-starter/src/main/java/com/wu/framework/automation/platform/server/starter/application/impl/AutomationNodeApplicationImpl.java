package com.wu.framework.automation.platform.server.starter.application.impl;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeApplication;
import com.wu.framework.automation.platform.server.starter.application.assembler.AutomationNodeDTOAssembler;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeDTO;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNodeRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryOneCommand;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class AutomationNodeApplicationImpl implements AutomationNodeApplication {

    @Autowired
    AutomationNodeRepository automationNodeRepository;
    /**
     * describe 新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> story(AutomationNodeStoryCommand automationNodeStoryCommand) {
        if(automationNodeStoryCommand.getId()==null){
            automationNodeStoryCommand.setId(UUID.randomUUID().toString());
        }
        AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeStoryCommand);
        return automationNodeRepository.story(automationNode);
    }
    /**
     * describe 更新自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> updateOne(AutomationNodeUpdateCommand automationNodeUpdateCommand) {
        AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeUpdateCommand);
        return automationNodeRepository.story(automationNode);
    }

    /**
     * describe 查询单个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNodeDTO> findOne(AutomationNodeQueryOneCommand automationNodeQueryOneCommand) {
        AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeQueryOneCommand);
        return automationNodeRepository.findOne(automationNode).convert(AutomationNodeDTOAssembler.INSTANCE::fromAutomationNode);
    }

    /**
     * describe 查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<List<AutomationNodeDTO>> findList(AutomationNodeQueryListCommand automationNodeQueryListCommand) {
        AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeQueryListCommand);
        return automationNodeRepository.findList(automationNode)        .convert(automationNodes -> automationNodes.stream().map(AutomationNodeDTOAssembler.INSTANCE::fromAutomationNode).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<LazyPage<AutomationNodeDTO>> findPage(int size,int current,AutomationNodeQueryListCommand automationNodeQueryListCommand) {
        AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeQueryListCommand);
        return automationNodeRepository.findPage(size,current,automationNode)        .convert(page -> page.convert(AutomationNodeDTOAssembler.INSTANCE::fromAutomationNode))            ;
    }

    /**
     * describe 删除自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Override
    public Result<AutomationNode> remove(AutomationNodeRemoveCommand automationNodeRemoveCommand) {
     AutomationNode automationNode = AutomationNodeDTOAssembler.INSTANCE.toAutomationNode(automationNodeRemoveCommand);
     return automationNodeRepository.remove(automationNode);
    }

}