package com.wu.framework.automation.platform.server.starter.application.impl;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeActionRecordApplication;
import com.wu.framework.automation.platform.server.starter.application.assembler.AutomationNodeActionRecordDTOAssembler;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeActionRecordDTO;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecord;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryOneCommand;

import java.util.stream.Collectors;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecordRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class AutomationNodeActionRecordApplicationImpl implements AutomationNodeActionRecordApplication {

    @Autowired
    AutomationNodeActionRecordRepository automationNodeActionRecordRepository;
    /**
     * describe 新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> story(AutomationNodeActionRecordStoryCommand automationNodeActionRecordStoryCommand) {
        AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordStoryCommand);
        return automationNodeActionRecordRepository.story(automationNodeActionRecord);
    }
    /**
     * describe 更新自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> updateOne(AutomationNodeActionRecordUpdateCommand automationNodeActionRecordUpdateCommand) {
        AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordUpdateCommand);
        return automationNodeActionRecordRepository.story(automationNodeActionRecord);
    }

    /**
     * describe 查询单个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecordDTO> findOne(AutomationNodeActionRecordQueryOneCommand automationNodeActionRecordQueryOneCommand) {
        AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordQueryOneCommand);
        return automationNodeActionRecordRepository.findOne(automationNodeActionRecord).convert(AutomationNodeActionRecordDTOAssembler.INSTANCE::fromAutomationNodeActionRecord);
    }

    /**
     * describe 查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<List<AutomationNodeActionRecordDTO>> findList(AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand) {
        AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordQueryListCommand);
        return automationNodeActionRecordRepository.findList(automationNodeActionRecord)        .convert(automationNodeActionRecords -> automationNodeActionRecords.stream().map(AutomationNodeActionRecordDTOAssembler.INSTANCE::fromAutomationNodeActionRecord).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<LazyPage<AutomationNodeActionRecordDTO>> findPage(int size,int current,AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand) {
        AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordQueryListCommand);
        return automationNodeActionRecordRepository.findPage(size,current,automationNodeActionRecord)        .convert(page -> page.convert(AutomationNodeActionRecordDTOAssembler.INSTANCE::fromAutomationNodeActionRecord))            ;
    }

    /**
     * describe 删除自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Override
    public Result<AutomationNodeActionRecord> remove(AutomationNodeActionRecordRemoveCommand automationNodeActionRecordRemoveCommand) {
     AutomationNodeActionRecord automationNodeActionRecord = AutomationNodeActionRecordDTOAssembler.INSTANCE.toAutomationNodeActionRecord(automationNodeActionRecordRemoveCommand);
     return automationNodeActionRecordRepository.remove(automationNodeActionRecord);
    }

}