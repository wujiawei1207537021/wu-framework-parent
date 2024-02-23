package com.wu.smart.acw.client.nocode.provider.application.assembler;

import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveCommand;
import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInfoSaveSQLCommand;
import com.wu.smart.acw.client.nocode.provider.application.dto.InterfaceInfoDTO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

/**
 * describe Dataway 中的API 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class InterfaceInfoDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/
    public static InterfaceInfo toInterfaceInfo(InterfaceInfoCommand interfaceInfoCommand) {
        if (null != interfaceInfoCommand) {
        InterfaceInfo interfaceInfo = new InterfaceInfo(); 
           interfaceInfo.setApiComment(interfaceInfoCommand.getApiComment());
           interfaceInfo.setApiId(interfaceInfoCommand.getApiId());
           interfaceInfo.setApiMethod(interfaceInfoCommand.getApiMethod());
           interfaceInfo.setApiPath(interfaceInfoCommand.getApiPath());
           interfaceInfo.setApiResultType(interfaceInfoCommand.getApiResultType());
           interfaceInfo.setApiStatus(interfaceInfoCommand.getApiStatus());
           interfaceInfo.setApiType(interfaceInfoCommand.getApiType());
           interfaceInfo.setExecuteType(interfaceInfoCommand.getExecuteType());
           interfaceInfo.setTag(interfaceInfoCommand.getTag());
           interfaceInfo.setSchema(interfaceInfoCommand.getSchema());
            if (!ObjectUtils.isEmpty(interfaceInfoCommand.getInterfaceTableCommandList())) {
                interfaceInfo.setInterfaceTableList(interfaceInfoCommand.getInterfaceTableCommandList().stream().map(InterfaceTableDTOAssembler::toInterfaceTable).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(interfaceInfoCommand.getInterfaceInParamCommandList())) {
                interfaceInfo.setInterfaceInParamList(interfaceInfoCommand.getInterfaceInParamCommandList().stream().map(InterfaceInParamDTOAssembler.INSTANCE::toInterfaceInParam).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(interfaceInfoCommand.getInterfaceOutParamCommandList())) {
                interfaceInfo.setInterfaceOutParamList(interfaceInfoCommand.getInterfaceOutParamCommandList().stream().map(InterfaceOutParamDTOAssembler::toInterfaceOutParam).collect(Collectors.toList()));
            }
            return interfaceInfo;
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
     * @date 2023/08/11 05:26 下午
     **/
    public static InterfaceInfoDTO fromInterfaceInfo(InterfaceInfo interfaceInfo) {
        if (null != interfaceInfo) {
        InterfaceInfoDTO interfaceInfoDTO = new InterfaceInfoDTO(); 
           interfaceInfoDTO.setApiComment(interfaceInfo.getApiComment());
           interfaceInfoDTO.setApiId(interfaceInfo.getApiId());
           interfaceInfoDTO.setApiMethod(interfaceInfo.getApiMethod());
           interfaceInfoDTO.setApiPath(interfaceInfo.getApiPath());
           interfaceInfoDTO.setApiResultType(interfaceInfo.getApiResultType());
           interfaceInfoDTO.setApiStatus(interfaceInfo.getApiStatus());
           interfaceInfoDTO.setApiType(interfaceInfo.getApiType());
           interfaceInfoDTO.setTag(interfaceInfo.getTag());
           interfaceInfoDTO.setSchema(interfaceInfo.getSchema());
            interfaceInfoDTO.setExecuteType(interfaceInfo.getExecuteType());
            return interfaceInfoDTO;
        }
        return null;
    }

    public static InterfaceInfo toInterfaceInfo(InterfaceInfoSaveCommand interfaceInfoSaveCommand) {
        if (null != interfaceInfoSaveCommand) {
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            interfaceInfo.setApiComment(interfaceInfoSaveCommand.getApiComment());
            interfaceInfo.setApiId(interfaceInfoSaveCommand.getApiId());
            interfaceInfo.setApiMethod(interfaceInfoSaveCommand.getApiMethod());
            interfaceInfo.setApiPath(interfaceInfoSaveCommand.getApiPath());
            interfaceInfo.setApiResultType(interfaceInfoSaveCommand.getApiResultType());
            interfaceInfo.setApiStatus(interfaceInfoSaveCommand.getApiStatus());
            interfaceInfo.setApiType(interfaceInfoSaveCommand.getApiType());
            interfaceInfo.setExecuteType(interfaceInfoSaveCommand.getExecuteType());
            interfaceInfo.setTag(interfaceInfoSaveCommand.getTag());
            interfaceInfo.setSchema(interfaceInfoSaveCommand.getSchema());
            if (!ObjectUtils.isEmpty(interfaceInfoSaveCommand.getInterfaceTableCommandList())) {
                interfaceInfo.setInterfaceTableList(interfaceInfoSaveCommand.getInterfaceTableCommandList().stream().map(InterfaceTableDTOAssembler::toInterfaceTable).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(interfaceInfoSaveCommand.getInterfaceInParamCommandList())) {
                interfaceInfo.setInterfaceInParamList(interfaceInfoSaveCommand.getInterfaceInParamCommandList().stream().map(InterfaceInParamDTOAssembler.INSTANCE::toInterfaceInParam).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(interfaceInfoSaveCommand.getInterfaceOutParamCommandList())) {
                interfaceInfo.setInterfaceOutParamList(interfaceInfoSaveCommand.getInterfaceOutParamCommandList().stream().map(InterfaceOutParamDTOAssembler::toInterfaceOutParam).collect(Collectors.toList()));
            }
            return interfaceInfo;
        }
        return null;
    }

    public static InterfaceInfo toInterfaceInfo(InterfaceInfoSaveSQLCommand interfaceInfoSaveSQLCommand) {
        if (null != interfaceInfoSaveSQLCommand) {
            InterfaceInfo interfaceInfo = new InterfaceInfo();
            interfaceInfo.setApiComment(interfaceInfoSaveSQLCommand.getApiComment());
            interfaceInfo.setApiId(interfaceInfoSaveSQLCommand.getApiId());
            interfaceInfo.setApiMethod(interfaceInfoSaveSQLCommand.getApiMethod());
            interfaceInfo.setApiPath(interfaceInfoSaveSQLCommand.getApiPath());
            interfaceInfo.setApiResultType(interfaceInfoSaveSQLCommand.getApiResultType());
            interfaceInfo.setApiStatus(interfaceInfoSaveSQLCommand.getApiStatus());
            interfaceInfo.setApiType(interfaceInfoSaveSQLCommand.getApiType());
            interfaceInfo.setTag(interfaceInfoSaveSQLCommand.getTag());
            interfaceInfo.setSchema(interfaceInfoSaveSQLCommand.getSchema());
             return interfaceInfo;
        }
        return null;
    }
}