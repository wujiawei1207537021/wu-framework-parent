package com.wu.framework.automation.platform.server.starter.controller;

import com.wu.framework.automation.platform.server.starter.application.AutomationNodeHttpActionApplication;
import com.wu.framework.automation.platform.server.starter.application.dto.AutomationNodeHttpActionDTO;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionStoryCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionRemoveCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionUpdateCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryListCommand;
import com.wu.framework.automation.platform.server.starter.application.command.automation.node.http.action.AutomationNodeHttpActionQueryOneCommand;

import java.util.List;
import java.util.Map;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 自动化http节点动作
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "自动化http节点动作提供者")
@EasyController("/lazy/automation/node/http/action")
public class AutomationNodeHttpActionProvider {

    @Autowired
    private AutomationNodeHttpActionApplication automationNodeHttpActionApplication;

    /**
     * describe 新增自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "新增自动化http节点动作")
    @PostMapping("/story")
    public Result<AutomationNodeHttpAction> story(@RequestBody AutomationNodeHttpActionStoryCommand automationNodeHttpActionStoryCommand) {
        return automationNodeHttpActionApplication.story(automationNodeHttpActionStoryCommand);
    }

    /**
     * describe 更新自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "更新自动化http节点动作")
    @PutMapping("/updateOne")
    public Result<AutomationNodeHttpAction> updateOne(@RequestBody AutomationNodeHttpActionUpdateCommand automationNodeHttpActionUpdateCommand) {
        return automationNodeHttpActionApplication.updateOne(automationNodeHttpActionUpdateCommand);
    }

    /**
     * describe 查询单个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "查询单个自动化http节点动作")
    @GetMapping("/findOne")
    public Result<AutomationNodeHttpActionDTO> findOne(@ModelAttribute AutomationNodeHttpActionQueryOneCommand automationNodeHttpActionQueryOneCommand) {
        return automationNodeHttpActionApplication.findOne(automationNodeHttpActionQueryOneCommand);
    }

    /**
     * describe 查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "查询多个自动化http节点动作")
    @GetMapping("/findList")
    public Result<List<AutomationNodeHttpActionDTO>> findList(@ModelAttribute AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand) {
        return automationNodeHttpActionApplication.findList(automationNodeHttpActionQueryListCommand);
    }

    /**
     * describe 分页查询多个自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "分页查询多个自动化http节点动作")
    @GetMapping("/findPage")
    public Result<LazyPage<AutomationNodeHttpActionDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                                  @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute AutomationNodeHttpActionQueryListCommand automationNodeHttpActionQueryListCommand) {
        return automationNodeHttpActionApplication.findPage(size, current, automationNodeHttpActionQueryListCommand);
    }

    /**
     * describe 删除自动化http节点动作
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/

    @Operation(summary = "删除自动化http节点动作")
    @DeleteMapping("/remove")
    public Result<AutomationNodeHttpAction> remove(@ModelAttribute AutomationNodeHttpActionRemoveCommand automationNodeHttpActionRemoveCommand) {
        return automationNodeHttpActionApplication.remove(automationNodeHttpActionRemoveCommand);
    }

    /**
     * 执行动作
     * @param actionId  动作ID
     * @return void
     */
    @Operation(summary = "执行动作")
    @PatchMapping("/executeOne/{actionId}")
    public Result<Map<String,Object>>  executeOne(@Parameter(description = "动作ID") @PathVariable String actionId) {
        return automationNodeHttpActionApplication.executeOne(actionId);
    }
}