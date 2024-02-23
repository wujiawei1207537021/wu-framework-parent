package com.wu.framework.automation.platform.server.starter.component.adapter;


import com.wu.framework.automation.platform.server.starter.component.AutomationNodeAction;
import com.wu.framework.automation.platform.server.starter.component.AutomationNodeHttpActionAdvancedTarget;
import com.wu.framework.response.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 动作处理适配器
 */
@Component
public class AutomationNodeActionAdapter {

    private final List<AutomationNodeHttpActionAdvancedTarget> automationNodeHttpActionRepositoryList;

    public AutomationNodeActionAdapter(List<AutomationNodeHttpActionAdvancedTarget> automationNodeHttpActionRepositoryList) {
        this.automationNodeHttpActionRepositoryList = automationNodeHttpActionRepositoryList;
    }


    /**
     * 节点执行
     *
     * @param action 执行
     */
    public Result<Map<String,Object>> execute(AutomationNodeAction action) {
        for (AutomationNodeHttpActionAdvancedTarget automationNodeHttpActionRepository : automationNodeHttpActionRepositoryList) {
            if (automationNodeHttpActionRepository.support(action)) {
                return automationNodeHttpActionRepository.execute(action);
            }
        }
        return null;
    }

}
