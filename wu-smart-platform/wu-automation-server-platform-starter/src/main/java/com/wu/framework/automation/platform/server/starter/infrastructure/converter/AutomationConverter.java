package com.wu.framework.automation.platform.server.starter.infrastructure.converter;

import com.wu.framework.automation.platform.server.starter.domain.model.automation.Automation;
import com.wu.framework.automation.platform.server.starter.infrastructure.entity.AutomationDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 自动化 
 *
 * @author Jia wei Wu
 * @date 2023/11/06 01:57 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AutomationConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
    AutomationConverter INSTANCE = Mappers.getMapper(AutomationConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
    Automation toAutomation(AutomationDO automationDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/11/06 01:57 下午
     **/
     AutomationDO fromAutomation(Automation automation); 
}