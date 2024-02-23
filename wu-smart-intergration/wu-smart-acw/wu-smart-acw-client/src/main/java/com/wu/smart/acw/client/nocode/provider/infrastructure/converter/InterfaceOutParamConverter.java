package com.wu.smart.acw.client.nocode.provider.infrastructure.converter;


import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceOutParamDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.out.param.InterfaceOutParam;

/**
 * describe 接口输出参数 
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/

public class InterfaceOutParamConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/
    public static InterfaceOutParam toInterfaceOutParam(InterfaceOutParamDO interfaceOutParamDO) {
        if (null != interfaceOutParamDO) {
        InterfaceOutParam interfaceOutParam = new InterfaceOutParam(); 
           interfaceOutParam.setApiId(interfaceOutParamDO.getApiId());
           interfaceOutParam.setCreateTime(interfaceOutParamDO.getCreateTime());
           interfaceOutParam.setId(interfaceOutParamDO.getId());
           interfaceOutParam.setMappingName(interfaceOutParamDO.getMappingName());
           interfaceOutParam.setName(interfaceOutParamDO.getName());
           interfaceOutParam.setUpdateTime(interfaceOutParamDO.getUpdateTime());
            return interfaceOutParam;
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
     * @date 2023/08/13 06:48 晚上
     **/
    public static InterfaceOutParamDO fromInterfaceOutParam(InterfaceOutParam interfaceOutParam) {
        if (null != interfaceOutParam) {
        InterfaceOutParamDO interfaceOutParamDO = new InterfaceOutParamDO(); 
           interfaceOutParamDO.setApiId(interfaceOutParam.getApiId());
           interfaceOutParamDO.setCreateTime(interfaceOutParam.getCreateTime());
           interfaceOutParamDO.setId(interfaceOutParam.getId());
           interfaceOutParamDO.setMappingName(interfaceOutParam.getMappingName());
           interfaceOutParamDO.setName(interfaceOutParam.getName());
           interfaceOutParamDO.setUpdateTime(interfaceOutParam.getUpdateTime());
            return interfaceOutParamDO;
        }
        return null;
    }

}