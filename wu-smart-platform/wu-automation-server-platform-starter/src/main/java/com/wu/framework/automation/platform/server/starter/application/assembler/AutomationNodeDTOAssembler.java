package com.wu.framework.automation.platform.server.starter.application.assembler;

import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeDTO;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryOneCommand;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AutomationNodeDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
    AutomationNodeDTOAssembler INSTANCE = Mappers.getMapper(AutomationNodeDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNode toAutomationNode(AutomationNodeStoryCommand automationNodeStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNode toAutomationNode(AutomationNodeUpdateCommand automationNodeUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNode toAutomationNode(AutomationNodeQueryOneCommand automationNodeQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNode toAutomationNode(AutomationNodeQueryListCommand automationNodeQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNode toAutomationNode(AutomationNodeRemoveCommand automationNodeRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNodeDTO fromAutomationNode(AutomationNode automationNode);
}