package com.wu.smart.acw.client.nocode.provider.application.assembler;


import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceOutParamCommand;
import com.wu.smart.acw.client.nocode.provider.application.dto.InterfaceOutParamDTO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;

/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class InterfaceOutParamDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/
    public static InterfaceOutParam toInterfaceOutParam(InterfaceOutParamCommand interfaceOutParamCommand) {
        if (null != interfaceOutParamCommand) {
        InterfaceOutParam interfaceOutParam = new InterfaceOutParam(); 
           interfaceOutParam.setApiId(interfaceOutParamCommand.getApiId());
           interfaceOutParam.setCreateTime(interfaceOutParamCommand.getCreateTime());
           interfaceOutParam.setId(interfaceOutParamCommand.getId());
           interfaceOutParam.setMappingName(interfaceOutParamCommand.getMappingName());
           interfaceOutParam.setName(interfaceOutParamCommand.getName());
           interfaceOutParam.setUpdateTime(interfaceOutParamCommand.getUpdateTime());
            return interfaceOutParam;
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
     * @date 2023/08/13 06:48 晚上
     **/
    public static InterfaceOutParamDTO fromInterfaceOutParam(InterfaceOutParam interfaceOutParam) {
        if (null != interfaceOutParam) {
        InterfaceOutParamDTO interfaceOutParamDTO = new InterfaceOutParamDTO(); 
           interfaceOutParamDTO.setApiId(interfaceOutParam.getApiId());
           interfaceOutParamDTO.setCreateTime(interfaceOutParam.getCreateTime());
           interfaceOutParamDTO.setId(interfaceOutParam.getId());
           interfaceOutParamDTO.setMappingName(interfaceOutParam.getMappingName());
           interfaceOutParamDTO.setName(interfaceOutParam.getName());
           interfaceOutParamDTO.setUpdateTime(interfaceOutParam.getUpdateTime());
            return interfaceOutParamDTO;
        }
        return null;
    }

}