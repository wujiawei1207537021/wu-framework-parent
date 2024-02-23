package com.wu.framework.automation.platform.server.starter.application.impl;


import com.wu.framework.automation.platform.server.starter.application.AutomationApplication;
import com.wu.framework.automation.platform.server.starter.application.assembler.AutomationDTOAssembler;
import com.wu.framework.automation.platform.server.starter.application.command.automation.*;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationDTO;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.AutomationRepository;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * describe 自动化
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class AutomationApplicationImpl implements AutomationApplication {

    @Autowired
    AutomationRepository automationRepository;

    /**
     * describe 新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> story(AutomationStoryCommand automationStoryCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationStoryCommand);
        if(ObjectUtils.isEmpty(automation.getId())){
            automation.setId(UUID.randomUUID().toString());
        }

        automation.setIsDeleted(false);
        return automationRepository.story(automation);
    }

    /**
     * describe 更新自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> updateOne(AutomationUpdateCommand automationUpdateCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationUpdateCommand);
        return automationRepository.story(automation);
    }

    /**
     * describe 查询单个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<AutomationDTO> findOne(AutomationQueryOneCommand automationQueryOneCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationQueryOneCommand);
        return automationRepository.findOne(automation).convert(AutomationDTOAssembler.INSTANCE::fromAutomation).convert(automationDTO -> {
            if(!ObjectUtils.isEmpty(automationDTO.getAutomationNodeList())){
                automationDTO.setAutomationNodeList(
                        automationDTO
                                .getAutomationNodeList()
                                .stream()
                                .sorted(Comparator.comparing(AutomationNode::getSort))
                                .toList()
                );
            }

            return automationDTO;
        });
    }

    /**
     * describe 查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<List<AutomationDTO>> findList(AutomationQueryListCommand automationQueryListCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationQueryListCommand);
        return automationRepository.findList(automation).convert(automations -> automations.stream().map(AutomationDTOAssembler.INSTANCE::fromAutomation).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<LazyPage<AutomationDTO>> findPage(int size, int current, AutomationQueryListCommand automationQueryListCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationQueryListCommand);
        return automationRepository.findPage(size, current, automation).convert(page -> page.convert(AutomationDTOAssembler.INSTANCE::fromAutomation));
    }

    /**
     * describe 删除自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Override
    public Result<Automation> remove(AutomationRemoveCommand automationRemoveCommand) {
        Automation automation = AutomationDTOAssembler.INSTANCE.toAutomation(automationRemoveCommand);
        return automationRepository.remove(automation);
    }

    /**
     * 执行自动化
     *
     * @param automationExecuteCommand 执行自动化
     * @return 执行自动化结果
     */
    @Override
    public Result<Automation> execute(AutomationExecuteCommand automationExecuteCommand) {

        // 查询自动化节点

        // 获取自动化节点动作
        // 执行动作

        return null;
    }
}