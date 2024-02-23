package com.wu.framework.automation.platform.server.starter.component;

import com.wu.framework.response.Result;

import java.util.Map;

public interface AutomationNodeActionAdvanced {

    /**
     * 是否支持
     * @param automationNodeAction
     * @return
     */
    boolean support(AutomationNodeAction automationNodeAction);

    /**
     * 节点执行
     *
     * @param action 执行
     */
    Result<Map<String,Object>> execute(AutomationNodeAction action);
}
