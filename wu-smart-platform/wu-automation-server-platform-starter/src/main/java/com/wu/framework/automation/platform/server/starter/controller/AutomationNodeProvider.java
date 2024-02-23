package com.wu.framework.automation.platform.server.starter.controller;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeApplication;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeDTO;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeStoryCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.AutomationNodeRemoveCommand;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "自动化节点提供者")
@EasyController("/lazy/automation/node")
public class AutomationNodeProvider  {

    @Autowired
    private AutomationNodeApplication automationNodeApplication;

    /**
     * describe 新增自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "新增自动化节点")
    @PostMapping("/story")
    public Result<AutomationNode> story(@RequestBody AutomationNodeStoryCommand automationNodeStoryCommand){
        return automationNodeApplication.story(automationNodeStoryCommand);
    }
    /**
     * describe 更新自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "更新自动化节点")
    @PutMapping("/updateOne")
    public Result<AutomationNode> updateOne(@RequestBody AutomationNodeUpdateCommand automationNodeUpdateCommand){
        return automationNodeApplication.updateOne(automationNodeUpdateCommand);
    }
    /**
     * describe 查询单个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "查询单个自动化节点")
    @GetMapping("/findOne")
    public Result<AutomationNodeDTO> findOne(@ModelAttribute AutomationNodeQueryOneCommand automationNodeQueryOneCommand){
        return automationNodeApplication.findOne(automationNodeQueryOneCommand);
    }
    /**
     * describe 查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "查询多个自动化节点")
    @GetMapping("/findList")
    public Result<List<AutomationNodeDTO>> findList(@ModelAttribute AutomationNodeQueryListCommand automationNodeQueryListCommand){
        return automationNodeApplication.findList(automationNodeQueryListCommand);
    }
    /**
     * describe 分页查询多个自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "分页查询多个自动化节点")
    @GetMapping("/findPage")
    public Result<LazyPage<AutomationNodeDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute AutomationNodeQueryListCommand automationNodeQueryListCommand){
        return automationNodeApplication.findPage(size,current,automationNodeQueryListCommand);
    }
    /**
     * describe 删除自动化节点
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/

    @Operation(summary = "删除自动化节点")
    @DeleteMapping("/remove")
    public Result<AutomationNode> remove(@ModelAttribute AutomationNodeRemoveCommand automationNodeRemoveCommand){
        return automationNodeApplication.remove(automationNodeRemoveCommand);
    }
}