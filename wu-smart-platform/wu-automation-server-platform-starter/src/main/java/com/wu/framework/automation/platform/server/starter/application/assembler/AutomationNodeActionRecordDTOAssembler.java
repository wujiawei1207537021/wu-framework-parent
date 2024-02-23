package com.wu.framework.automation.platform.server.starter.application.assembler;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecord;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryOneCommand;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeActionRecordDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AutomationNodeActionRecordDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
    AutomationNodeActionRecordDTOAssembler INSTANCE = Mappers.getMapper(AutomationNodeActionRecordDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordStoryCommand automationNodeActionRecordStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordUpdateCommand automationNodeActionRecordUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordQueryOneCommand automationNodeActionRecordQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordRemoveCommand automationNodeActionRecordRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecordDTO fromAutomationNodeActionRecord(AutomationNodeActionRecord automationNodeActionRecord);
}