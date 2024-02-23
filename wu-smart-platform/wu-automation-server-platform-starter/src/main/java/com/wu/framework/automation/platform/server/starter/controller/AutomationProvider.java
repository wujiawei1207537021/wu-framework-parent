package com.wu.framework.automation.platform.server.starter.controller;

import com.wu.framework.automation.platform.server.starter.application.AutomationApplication;

import com.wu.framework.automation.platform.server.starter.application.dto.AutomationDTO;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.automation.platform.server.starter.application.command.automation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "自动化提供者")
@EasyController("/lazy/automation")
public class AutomationProvider  {

    @Autowired
    private AutomationApplication automationApplication;

    /**
     * describe 新增自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Operation(summary = "新增自动化")
    @PostMapping("/story")
    public Result<Automation> story(@RequestBody AutomationStoryCommand automationStoryCommand){
        return automationApplication.story(automationStoryCommand);
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

    @Operation(summary = "更新自动化")
    @PutMapping("/updateOne")
    public Result<Automation> updateOne(@RequestBody AutomationUpdateCommand automationUpdateCommand){
        return automationApplication.updateOne(automationUpdateCommand);
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

    @Operation(summary = "查询单个自动化")
    @GetMapping("/findOne")
    public Result<AutomationDTO> findOne(@ModelAttribute AutomationQueryOneCommand automationQueryOneCommand){
        return automationApplication.findOne(automationQueryOneCommand);
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

    @Operation(summary = "查询多个自动化")
    @GetMapping("/findList")
    public Result<List<AutomationDTO>> findList(@ModelAttribute AutomationQueryListCommand automationQueryListCommand){
        return automationApplication.findList(automationQueryListCommand);
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

    @Operation(summary = "分页查询多个自动化")
    @GetMapping("/findPage")
    public Result<LazyPage<AutomationDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute AutomationQueryListCommand automationQueryListCommand){
        return automationApplication.findPage(size,current,automationQueryListCommand);
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

    @Operation(summary = "删除自动化")
    @DeleteMapping("/remove")
    public Result<Automation> remove(@ModelAttribute AutomationRemoveCommand automationRemoveCommand){
        return automationApplication.remove(automationRemoveCommand);
    }

    /**
     * describe 执行自动化
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/

    @Operation(summary = "执行自动化")
    @PutMapping("/execute")
    public Result<Automation> execute(@ModelAttribute AutomationExecuteCommand automationExecuteCommand){
        return automationApplication.execute(automationExecuteCommand);
    }
}