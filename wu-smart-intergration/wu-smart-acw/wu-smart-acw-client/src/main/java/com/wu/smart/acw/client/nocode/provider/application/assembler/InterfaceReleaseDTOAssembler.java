package com.wu.smart.acw.client.nocode.provider.application.assembler;

import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceReleaseCommand;
import com.wu.smart.acw.client.nocode.provider.application.dto.InterfaceReleaseDTO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;

/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/

public class InterfaceReleaseDTOAssembler {


    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/
    public static InterfaceRelease toInterfaceRelease(InterfaceReleaseCommand interfaceReleaseCommand) {
        if (null != interfaceReleaseCommand) {
        InterfaceRelease interfaceRelease = new InterfaceRelease(); 
           interfaceRelease.setPubApiId(interfaceReleaseCommand.getPubApiId());
           interfaceRelease.setPubId(interfaceReleaseCommand.getPubId());
           interfaceRelease.setPubMethod(interfaceReleaseCommand.getPubMethod());
           interfaceRelease.setPubPath(interfaceReleaseCommand.getPubPath());
           interfaceRelease.setPubReleaseTime(interfaceReleaseCommand.getPubReleaseTime());
           interfaceRelease.setPubSample(interfaceReleaseCommand.getPubSample());
           interfaceRelease.setPubSchema(interfaceReleaseCommand.getPubSchema());
           interfaceRelease.setPubScript(interfaceReleaseCommand.getPubScript());
           interfaceRelease.setPubScriptOri(interfaceReleaseCommand.getPubScriptOri());
           interfaceRelease.setPubStatus(interfaceReleaseCommand.getPubStatus());
           interfaceRelease.setPubType(interfaceReleaseCommand.getPubType());
            return interfaceRelease;
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
    public static InterfaceReleaseDTO fromInterfaceRelease(InterfaceRelease interfaceRelease) {
        if (null != interfaceRelease) {
        InterfaceReleaseDTO interfaceReleaseDTO = new InterfaceReleaseDTO(); 
           interfaceReleaseDTO.setPubApiId(interfaceRelease.getPubApiId());
           interfaceReleaseDTO.setPubId(interfaceRelease.getPubId());
           interfaceReleaseDTO.setPubMethod(interfaceRelease.getPubMethod());
           interfaceReleaseDTO.setPubPath(interfaceRelease.getPubPath());
           interfaceReleaseDTO.setPubReleaseTime(interfaceRelease.getPubReleaseTime());
           interfaceReleaseDTO.setPubSample(interfaceRelease.getPubSample());
           interfaceReleaseDTO.setPubSchema(interfaceRelease.getPubSchema());
           interfaceReleaseDTO.setPubScript(interfaceRelease.getPubScript());
           interfaceReleaseDTO.setPubScriptOri(interfaceRelease.getPubScriptOri());
           interfaceReleaseDTO.setPubStatus(interfaceRelease.getPubStatus());
           interfaceReleaseDTO.setPubType(interfaceRelease.getPubType());
            return interfaceReleaseDTO;
        }
        return null;
    }

}