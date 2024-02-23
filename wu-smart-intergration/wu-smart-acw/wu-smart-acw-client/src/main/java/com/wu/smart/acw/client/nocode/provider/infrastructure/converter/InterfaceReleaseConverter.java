package com.wu.smart.acw.client.nocode.provider.infrastructure.converter;

import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceReleaseDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.release.InterfaceRelease;

/**
 * describe Dataway API 发布历史。 
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class InterfaceReleaseConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/
    public static InterfaceRelease toInterfaceRelease(InterfaceReleaseDO interfaceReleaseDO) {
        if (null != interfaceReleaseDO) {
        InterfaceRelease interfaceRelease = new InterfaceRelease(); 
           interfaceRelease.setPubApiId(interfaceReleaseDO.getPubApiId());
           interfaceRelease.setPubId(interfaceReleaseDO.getPubId());
           interfaceRelease.setPubMethod(interfaceReleaseDO.getPubMethod());
           interfaceRelease.setPubPath(interfaceReleaseDO.getPubPath());
           interfaceRelease.setPubReleaseTime(interfaceReleaseDO.getPubReleaseTime());
           interfaceRelease.setPubSample(interfaceReleaseDO.getPubSample());
           interfaceRelease.setPubSchema(interfaceReleaseDO.getPubSchema());
           interfaceRelease.setPubScript(interfaceReleaseDO.getPubScript());
           interfaceRelease.setPubScriptOri(interfaceReleaseDO.getPubScriptOri());
           interfaceRelease.setPubStatus(interfaceReleaseDO.getPubStatus());
           interfaceRelease.setPubType(interfaceReleaseDO.getPubType());
            return interfaceRelease;
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
     * @date 2023/08/11 05:26 下午
     **/
    public static InterfaceReleaseDO fromInterfaceRelease(InterfaceRelease interfaceRelease) {
        if (null != interfaceRelease) {
        InterfaceReleaseDO interfaceReleaseDO = new InterfaceReleaseDO(); 
           interfaceReleaseDO.setPubApiId(interfaceRelease.getPubApiId());
           interfaceReleaseDO.setPubId(interfaceRelease.getPubId());
           interfaceReleaseDO.setPubMethod(interfaceRelease.getPubMethod());
           interfaceReleaseDO.setPubPath(interfaceRelease.getPubPath());
           interfaceReleaseDO.setPubReleaseTime(interfaceRelease.getPubReleaseTime());
           interfaceReleaseDO.setPubSample(interfaceRelease.getPubSample());
           interfaceReleaseDO.setPubSchema(interfaceRelease.getPubSchema());
           interfaceReleaseDO.setPubScript(interfaceRelease.getPubScript());
           interfaceReleaseDO.setPubScriptOri(interfaceRelease.getPubScriptOri());
           interfaceReleaseDO.setPubStatus(interfaceRelease.getPubStatus());
           interfaceReleaseDO.setPubType(interfaceRelease.getPubType());
            return interfaceReleaseDO;
        }
        return null;
    }

}