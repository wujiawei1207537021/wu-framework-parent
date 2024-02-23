package com.wu.framework.automation.platform.server.starter.infrastructure.converter;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.http.action.AutomationNodeHttpAction;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeHttpActionDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化http节点动作 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:09 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AutomationNodeHttpActionConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
    AutomationNodeHttpActionConverter INSTANCE = Mappers.getMapper(AutomationNodeHttpActionConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
    AutomationNodeHttpAction toAutomationNodeHttpAction(AutomationNodeHttpActionDO automationNodeHttpActionDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:09 下午
     **/
     AutomationNodeHttpActionDO fromAutomationNodeHttpAction(AutomationNodeHttpAction automationNodeHttpAction); 
}