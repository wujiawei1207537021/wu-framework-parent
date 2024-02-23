package com.wu.smart.acw.client.nocode.provider.infrastructure.converter;

import com.wu.smart.acw.client.nocode.provider.infrastructure.entity.InterfaceInfoDO;
import com.wu.smart.acw.client.nocode.provider.model.interface_.info.InterfaceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe Dataway 中的API
 *
 * @author Jia wei Wu
 * @date 2023/08/11 05:26 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper()
public interface InterfaceInfoConverter {
    InterfaceInfoConverter INSTANCE = Mappers.getMapper(InterfaceInfoConverter.class);



    /**
     * describe 实体对象 转换成领域对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/
    InterfaceInfo toInterfaceInfo(InterfaceInfoDO interfaceInfoDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2023/08/11 05:26 下午
     **/
    InterfaceInfoDO fromInterfaceInfo(InterfaceInfo interfaceInfo);

}