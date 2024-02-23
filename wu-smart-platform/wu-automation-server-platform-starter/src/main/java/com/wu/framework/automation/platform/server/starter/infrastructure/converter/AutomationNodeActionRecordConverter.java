package com.wu.framework.automation.platform.server.starter.infrastructure.converter;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.node.action.record.AutomationNodeActionRecord;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationNodeActionRecordDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化记录 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 02:08 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AutomationNodeActionRecordConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
    AutomationNodeActionRecordConverter INSTANCE = Mappers.getMapper(AutomationNodeActionRecordConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
    AutomationNodeActionRecord toAutomationNodeActionRecord(AutomationNodeActionRecordDO automationNodeActionRecordDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 02:08 下午
     **/
     AutomationNodeActionRecordDO fromAutomationNodeActionRecord(AutomationNodeActionRecord automationNodeActionRecord); 
}