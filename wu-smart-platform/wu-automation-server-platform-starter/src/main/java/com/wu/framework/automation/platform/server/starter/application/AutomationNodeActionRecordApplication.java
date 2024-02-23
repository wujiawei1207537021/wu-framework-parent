package com.wu.framework.automation.platform.server.starter.application;

import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeActionRecordDTO;
import com.wu.framework.response.Result;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecord;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordStoryCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.action.record.AutomationNodeActionRecordQueryOneCommand;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AutomationNodeActionRecordApplication {


    /**
     * describe 新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> story(AutomationNodeActionRecordStoryCommand automationNodeActionRecordStoryCommand);

    /**
     * describe 更新自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> updateOne(AutomationNodeActionRecordUpdateCommand automationNodeActionRecordUpdateCommand);

    /**
     * describe 查询单个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecordDTO> findOne(AutomationNodeActionRecordQueryOneCommand automationNodeActionRecordQueryOneCommand);

    /**
     * describe 查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result <List<AutomationNodeActionRecordDTO>> findList(AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand);

    /**
     * describe 分页查询多个自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result <LazyPage<AutomationNodeActionRecordDTO>> findPage(int size,int current,AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand);

    /**
     * describe 删除自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    Result<AutomationNodeActionRecord> remove(AutomationNodeActionRecordRemoveCommand automationNodeActionRecordRemoveCommand);

}