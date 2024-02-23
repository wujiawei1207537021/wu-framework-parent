package com.wu.framework.automation.platform.server.starter.controller;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeActionRecordApplication;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeActionRecordDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "自动化记录提供者")
@EasyController("/lazy/automation/node/action/record")
public class AutomationNodeActionRecordProvider  {

    @Autowired
    private AutomationNodeActionRecordApplication automationNodeActionRecordApplication;

    /**
     * describe 新增自动化记录
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/

    @Operation(summary = "新增自动化记录")
    @PostMapping("/story")
    public Result<AutomationNodeActionRecord> story(@RequestBody AutomationNodeActionRecordStoryCommand automationNodeActionRecordStoryCommand){
        return automationNodeActionRecordApplication.story(automationNodeActionRecordStoryCommand);
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

    @Operation(summary = "更新自动化记录")
    @PutMapping("/updateOne")
    public Result<AutomationNodeActionRecord> updateOne(@RequestBody AutomationNodeActionRecordUpdateCommand automationNodeActionRecordUpdateCommand){
        return automationNodeActionRecordApplication.updateOne(automationNodeActionRecordUpdateCommand);
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

    @Operation(summary = "查询单个自动化记录")
    @GetMapping("/findOne")
    public Result<AutomationNodeActionRecordDTO> findOne(@ModelAttribute AutomationNodeActionRecordQueryOneCommand automationNodeActionRecordQueryOneCommand){
        return automationNodeActionRecordApplication.findOne(automationNodeActionRecordQueryOneCommand);
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

    @Operation(summary = "查询多个自动化记录")
    @GetMapping("/findList")
    public Result<List<AutomationNodeActionRecordDTO>> findList(@ModelAttribute AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand){
        return automationNodeActionRecordApplication.findList(automationNodeActionRecordQueryListCommand);
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

    @Operation(summary = "分页查询多个自动化记录")
    @GetMapping("/findPage")
    public Result<LazyPage<AutomationNodeActionRecordDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute AutomationNodeActionRecordQueryListCommand automationNodeActionRecordQueryListCommand){
        return automationNodeActionRecordApplication.findPage(size,current,automationNodeActionRecordQueryListCommand);
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

    @Operation(summary = "删除自动化记录")
    @DeleteMapping("/remove")
    public Result<AutomationNodeActionRecord> remove(@ModelAttribute AutomationNodeActionRecordRemoveCommand automationNodeActionRecordRemoveCommand){
        return automationNodeActionRecordApplication.remove(automationNodeActionRecordRemoveCommand);
    }
}