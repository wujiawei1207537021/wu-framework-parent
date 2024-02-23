package com.wu.framework.automation.platform.server.starter.component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * describe 自动化节点动作
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
public interface AutomationNodeAction {

    /**
     * 获取 当前执行类型
     * @return
     */
    AutomationNodeActionType getAutomationNodeActionType();


    String getAutomationNodeId();

    String getBody();

    LocalDateTime getCreateTime();

    Map<String, String> getHeaders();

    String getHttpMethod();


    Map<String, String> getParams();


    String getUrl();


}