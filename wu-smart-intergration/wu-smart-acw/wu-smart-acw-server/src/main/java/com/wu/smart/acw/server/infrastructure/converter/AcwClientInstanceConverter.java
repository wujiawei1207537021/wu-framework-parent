package com.wu.smart.acw.server.infrastructure.converter;


import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import com.wu.smart.acw.server.infrastructure.entity.AcwClientInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface AcwClientInstanceConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
    AcwClientInstanceConverter INSTANCE = Mappers.getMapper(AcwClientInstanceConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param acwClientInstanceDO 客户端实例实体对象     
     * @return {@link AcwClientInstance} 客户端实例领域对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
    AcwClientInstance toAcwClientInstance(AcwClientInstanceDO acwClientInstanceDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param acwClientInstance 客户端实例领域对象     
     * @return {@link AcwClientInstanceDO} 客户端实例实体对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstanceDO fromAcwClientInstance(AcwClientInstance acwClientInstance); 
}