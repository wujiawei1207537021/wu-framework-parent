package com.wu.framework.automation.platform.server.starter.application.assembler;

import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionStoryCommand;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryOneCommand;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeHttpActionDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化http节点动作 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AutomationNodeHttpActionDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
    AutomationNodeHttpActionDTOAssembler INSTANCE = Mappers.getMapper(AutomationNodeHttpActionDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionStoryCommand automationNodeHttpActionStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionUpdateCommand automationNodeHttpActionUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionQueryOneCommand automationNodeHttpActionQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionRemoveCommand automationNodeHttpActionRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpActionDTO fromAutomationNodeHttpAction(AutomationNodeHttpAction automationNodeHttpAction);
}