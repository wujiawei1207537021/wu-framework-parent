package com.wu.smart.acw.client.nocode.provider.application.assembler;


import com.wu.smart.acw.client.nocode.provider.application.command.InterfaceInParamCommand;
import com.wu.smart.acw.client.nocode.provider.application.dto.InterfaceInParamDTO;
import com.wu.smart.acw.client.nocode.provider.infrastructure.converter.InterfaceInfoConverter;
import com.wu.smart.acw.client.nocode.provider.model.interface_.in.param.InterfaceInParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 接口输入参数
 *
 * @author Jia wei Wu
 * @date 2023/08/13 06:48 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface InterfaceInParamDTOAssembler {

    InterfaceInParamDTOAssembler INSTANCE = Mappers.getMapper(InterfaceInParamDTOAssembler.class);
    /**
     * describe 应用层入参转换成 持久层入参
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/
    InterfaceInParam toInterfaceInParam(InterfaceInParamCommand interfaceInParamCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/13 06:48 晚上
     **/
     InterfaceInParamDTO fromInterfaceInParam(InterfaceInParam interfaceInParam);

}