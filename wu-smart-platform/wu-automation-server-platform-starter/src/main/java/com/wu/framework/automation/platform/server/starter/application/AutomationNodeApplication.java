package com.wu.framework.automation.platform.server.starter.application;

import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeDTO;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.response.Result;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeQueryOneCommand;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AutomationNodeApplication {


    /**
     * describe 新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> story(AutomationNodeStoryCommand automationNodeStoryCommand);

    /**
     * describe 更新自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> updateOne(AutomationNodeUpdateCommand automationNodeUpdateCommand);

    /**
     * describe 查询单个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNodeDTO> findOne(AutomationNodeQueryOneCommand automationNodeQueryOneCommand);

    /**
     * describe 查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result <List<AutomationNodeDTO>> findList(AutomationNodeQueryListCommand automationNodeQueryListCommand);

    /**
     * describe 分页查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result <LazyPage<AutomationNodeDTO>> findPage(int size,int current,AutomationNodeQueryListCommand automationNodeQueryListCommand);

    /**
     * describe 删除自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    Result<AutomationNode> remove(AutomationNodeRemoveCommand automationNodeRemoveCommand);

}