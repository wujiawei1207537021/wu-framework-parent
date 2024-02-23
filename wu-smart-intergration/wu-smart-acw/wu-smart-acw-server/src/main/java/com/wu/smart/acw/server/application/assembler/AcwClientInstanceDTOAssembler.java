package com.wu.smart.acw.server.application.assembler;


import com.wu.smart.acw.server.application.command.acw.acw.client.instance.*;
import com.wu.smart.acw.server.application.dto.AcwClientInstanceDTO;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface AcwClientInstanceDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
    AcwClientInstanceDTOAssembler INSTANCE = Mappers.getMapper(AcwClientInstanceDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param acwClientInstanceStoryCommand 保存客户端实例对象     
     * @return {@link AcwClientInstance} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstance toAcwClientInstance(AcwClientInstanceStoryCommand acwClientInstanceStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param acwClientInstanceUpdateCommand 更新客户端实例对象     
     * @return {@link AcwClientInstance} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstance toAcwClientInstance(AcwClientInstanceUpdateCommand acwClientInstanceUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param acwClientInstanceQueryOneCommand 查询单个客户端实例对象参数     
     * @return {@link AcwClientInstance} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstance toAcwClientInstance(AcwClientInstanceQueryOneCommand acwClientInstanceQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param acwClientInstanceQueryListCommand 查询集合客户端实例对象参数     
     * @return {@link AcwClientInstance} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstance toAcwClientInstance(AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param acwClientInstanceRemoveCommand 删除客户端实例对象参数     
     * @return {@link AcwClientInstance} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstance toAcwClientInstance(AcwClientInstanceRemoveCommand acwClientInstanceRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param acwClientInstance 客户端实例领域对象     
     * @return {@link AcwClientInstanceDTO} 客户端实例DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/
     AcwClientInstanceDTO fromAcwClientInstance(AcwClientInstance acwClientInstance);
}