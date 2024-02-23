package com.wu.framework.automation.platform.server.starter.application;


import com.wu.framework.automation.platform.server.starter.application.command.automation.*;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationDTO;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;

/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AutomationApplication {


    /**
     * describe 新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> story(AutomationStoryCommand automationStoryCommand);

    /**
     * describe 更新自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> updateOne(AutomationUpdateCommand automationUpdateCommand);

    /**
     * describe 查询单个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<AutomationDTO> findOne(AutomationQueryOneCommand automationQueryOneCommand);

    /**
     * describe 查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result <List<AutomationDTO>> findList(AutomationQueryListCommand automationQueryListCommand);

    /**
     * describe 分页查询多个自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result <LazyPage<AutomationDTO>> findPage(int size, int current, AutomationQueryListCommand automationQueryListCommand);

    /**
     * describe 删除自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    Result<Automation> remove(AutomationRemoveCommand automationRemoveCommand);

    /**
     * 执行自动化
     * @param automationExecuteCommand
     * @return
     */
    Result<Automation> execute(AutomationExecuteCommand automationExecuteCommand);
}