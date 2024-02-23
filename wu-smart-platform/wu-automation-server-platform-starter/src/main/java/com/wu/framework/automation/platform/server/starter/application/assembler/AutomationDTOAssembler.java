package com.wu.framework.automation.platform.server.starter.application.assembler;

import com.wu.framework.automation.platform.server.starter.application.command.automation.AutomationStoryCommand;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.automation.platform.server.starter.application.command.automation.AutomationRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.AutomationUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.AutomationQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.AutomationQueryOneCommand;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AutomationDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
    AutomationDTOAssembler INSTANCE = Mappers.getMapper(AutomationDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     Automation toAutomation(AutomationStoryCommand automationStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     Automation toAutomation(AutomationUpdateCommand automationUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     Automation toAutomation(AutomationQueryOneCommand automationQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     Automation toAutomation(AutomationQueryListCommand automationQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     Automation toAutomation(AutomationRemoveCommand automationRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     AutomationDTO fromAutomation(Automation automation);
}