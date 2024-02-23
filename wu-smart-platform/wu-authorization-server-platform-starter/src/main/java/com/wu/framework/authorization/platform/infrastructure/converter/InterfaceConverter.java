package com.wu.framework.authorization.platform.infrastructure.converter;

import com.wu.framework.authorization.platform.infrastructure.entity.InterfaceDO;
import com.wu.framework.authorization.platform.model.interface_.Interface;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class InterfaceConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Interface toInterface(InterfaceDO interface_DO) {
        if (null != interface_DO) {
            Interface interface_ = new Interface();
            interface_.setApplicationName(interface_DO.getApplicationName());
            interface_.setCreateTime(interface_DO.getCreateTime());
            interface_.setDescription(interface_DO.getDescription());
            interface_.setParentPath(interface_DO.getParentPath());
            interface_.setPath(interface_DO.getPath());
            interface_.setRequestMethods(interface_DO.getRequestMethods());
            interface_.setTag(interface_DO.getTag());
            interface_.setUpdateTime(interface_DO.getUpdateTime());
            return interface_;
        }
        return null;
    }

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static InterfaceDO fromInterface(Interface interface_) {
        if (null != interface_) {
            InterfaceDO interface_DO = new InterfaceDO();
            interface_DO.setApplicationName(interface_.getApplicationName());
            interface_DO.setCreateTime(interface_.getCreateTime());
            interface_DO.setDescription(interface_.getDescription());
            interface_DO.setParentPath(interface_.getParentPath());
            interface_DO.setPath(interface_.getPath());
            interface_DO.setRequestMethods(interface_.getRequestMethods());
            interface_DO.setTag(interface_.getTag());
            interface_DO.setUpdateTime(interface_.getUpdateTime());
            return interface_DO;
        }
        return null;
    }

}