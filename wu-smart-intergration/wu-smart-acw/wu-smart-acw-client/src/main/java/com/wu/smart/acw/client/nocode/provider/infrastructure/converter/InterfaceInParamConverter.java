package com.wu.smart.acw.client.nocode.provider.infrastructure.converter;


import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceInParamDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;

/**
 * describe 接口输入参数
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/

public class InterfaceInParamConverter {


    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/
    public static InterfaceInParam toInterfaceInParam(InterfaceInParamDO interfaceInParamDO) {
        if (null != interfaceInParamDO) {
            InterfaceInParam interfaceInParam = new InterfaceInParam();
            interfaceInParam.setTerm(interfaceInParamDO.getTerm());
            interfaceInParam.setType(interfaceInParamDO.getType());
            interfaceInParam.setApiId(interfaceInParamDO.getApiId());
            interfaceInParam.setCreateTime(interfaceInParamDO.getCreateTime());
            interfaceInParam.setDefaultValue(interfaceInParamDO.getDefaultValue());
            interfaceInParam.setDefaultValueType(interfaceInParamDO.getDefaultValueType());
            interfaceInParam.setId(interfaceInParamDO.getId());
            interfaceInParam.setName(interfaceInParamDO.getName());
            interfaceInParam.setUpdateTime(interfaceInParamDO.getUpdateTime());
            return interfaceInParam;
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
    public static InterfaceInParamDO fromInterfaceInParam(InterfaceInParam interfaceInParam) {
        if (null != interfaceInParam) {
            InterfaceInParamDO interfaceInParamDO = new InterfaceInParamDO();
            interfaceInParamDO.setTerm(interfaceInParam.getTerm());
            interfaceInParamDO.setType(interfaceInParam.getType());
            interfaceInParamDO.setApiId(interfaceInParam.getApiId());
            interfaceInParamDO.setCreateTime(interfaceInParam.getCreateTime());
            interfaceInParamDO.setDefaultValue(interfaceInParam.getDefaultValue());
            interfaceInParamDO.setDefaultValueType(interfaceInParam.getDefaultValueType());
            interfaceInParamDO.setId(interfaceInParam.getId());
            interfaceInParamDO.setName(interfaceInParam.getName());
            interfaceInParamDO.setUpdateTime(interfaceInParam.getUpdateTime());
            return interfaceInParamDO;
        }
        return null;
    }

}