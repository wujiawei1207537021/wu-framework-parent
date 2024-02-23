package com.wu.framework.authorization.platform.application.assembler;


import com.wu.framework.authorization.platform.application.command.InterfaceCommand;
import com.wu.framework.authorization.platform.application.dto.InterfaceDTO;
import com.wu.framework.authorization.platform.model.interface_.Interface;

/**
 * describe 接口
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/

public class InterfaceDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static Interface toInterface(InterfaceCommand interface_Command) {
        if (null != interface_Command) {
            Interface interface_ = new Interface();
            interface_.setApplicationName(interface_Command.getApplicationName());
            interface_.setCreateTime(interface_Command.getCreateTime());
            interface_.setDescription(interface_Command.getDescription());
            interface_.setParentPath(interface_Command.getParentPath());
            interface_.setPath(interface_Command.getPath());
            interface_.setRequestMethods(interface_Command.getRequestMethods());
            interface_.setTag(interface_Command.getTag());
            interface_.setUpdateTime(interface_Command.getUpdateTime());
            return interface_;
        }
        return null;
    }

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/05/17 11:19 晚上
     **/
    public static InterfaceDTO fromInterface(Interface interface_) {
        if (null != interface_) {
            InterfaceDTO interface_DTO = new InterfaceDTO();
            interface_DTO.setApplicationName(interface_.getApplicationName());
            interface_DTO.setCreateTime(interface_.getCreateTime());
            interface_DTO.setDescription(interface_.getDescription());
            interface_DTO.setParentPath(interface_.getParentPath());
            interface_DTO.setPath(interface_.getPath());
            interface_DTO.setRequestMethods(interface_.getRequestMethods());
            interface_DTO.setTag(interface_.getTag());
            interface_DTO.setUpdateTime(interface_.getUpdateTime());
            return interface_DTO;
        }
        return null;
    }

}