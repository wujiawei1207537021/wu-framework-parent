package com.wu.framework.automation.platform.server.starter.infrastructure.converter;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.AutomationNode;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 自动化节点 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:03 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AutomationNodeConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
    AutomationNodeConverter INSTANCE = Mappers.getMapper(AutomationNodeConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
    AutomationNode toAutomationNode(AutomationNodeDO automationNodeDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:03 下午
     **/
     AutomationNodeDO fromAutomationNode(AutomationNode automationNode); 
}