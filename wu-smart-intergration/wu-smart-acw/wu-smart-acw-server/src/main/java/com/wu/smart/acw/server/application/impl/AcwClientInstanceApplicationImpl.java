package com.wu.smart.acw.server.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.AcwClientInstanceApplication;
import com.wu.smart.acw.server.application.assembler.AcwClientInstanceDTOAssembler;
import com.wu.smart.acw.server.application.command.AcwGenerateLocalJavaCommand;
import com.wu.smart.acw.server.application.command.acw.acw.client.instance.*;
import com.wu.smart.acw.server.application.dto.AcwClientInstanceDTO;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstanceRepository;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 客户端实例 
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class AcwClientInstanceApplicationImpl implements AcwClientInstanceApplication {

    @Resource
    AcwClientInstanceRepository acwClientInstanceRepository;
    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstanceStoryCommand 新增客户端实例     
     * @return {@link Result< AcwClientInstance >} 客户端实例新增后领域对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> story(AcwClientInstanceStoryCommand acwClientInstanceStoryCommand) {
        AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceStoryCommand);
        return acwClientInstanceRepository.story(acwClientInstance);
    }
    /**
     * describe 批量新增客户端实例
     *
     * @param acwClientInstanceStoryCommandList 批量新增客户端实例     
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<List<AcwClientInstance>> batchStory(List<AcwClientInstanceStoryCommand> acwClientInstanceStoryCommandList) {
        List<AcwClientInstance> acwClientInstanceList = acwClientInstanceStoryCommandList.stream().map( AcwClientInstanceDTOAssembler.INSTANCE::toAcwClientInstance).collect(Collectors.toList());
        return acwClientInstanceRepository.batchStory(acwClientInstanceList);
    }
    /**
     * describe 更新客户端实例
     *
     * @param acwClientInstanceUpdateCommand 更新客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> updateOne(AcwClientInstanceUpdateCommand acwClientInstanceUpdateCommand) {
        AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceUpdateCommand);
        return acwClientInstanceRepository.story(acwClientInstance);
    }

    /**
     * describe 查询单个客户端实例
     *
     * @param acwClientInstanceQueryOneCommand 查询单个客户端实例     
     * @return {@link Result< AcwClientInstanceDTO >} 客户端实例DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstanceDTO> findOne(AcwClientInstanceQueryOneCommand acwClientInstanceQueryOneCommand) {
        AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceQueryOneCommand);
        return acwClientInstanceRepository.findOne(acwClientInstance).convert(AcwClientInstanceDTOAssembler.INSTANCE::fromAcwClientInstance);
    }

    /**
     * describe 查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 查询多个客户端实例     
     * @return {@link Result<List<AcwClientInstanceDTO>>} 客户端实例DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<List<AcwClientInstanceDTO>> findList(AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand) {
        AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceQueryListCommand);
        return acwClientInstanceRepository.findList(acwClientInstance)        .convert(acwClientInstances -> acwClientInstances.stream().map(AcwClientInstanceDTOAssembler.INSTANCE::fromAcwClientInstance).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 分页查询多个客户端实例     
     * @return {@link Result< LazyPage <AcwClientInstanceDTO>>} 分页客户端实例DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<LazyPage<AcwClientInstanceDTO>> findPage(int size,int current,AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand) {
        AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceQueryListCommand);
        return acwClientInstanceRepository.findPage(size,current,acwClientInstance)        .convert(page -> page.convert(AcwClientInstanceDTOAssembler.INSTANCE::fromAcwClientInstance))            ;
    }

    /**
     * describe 删除客户端实例
     *
     * @param acwClientInstanceRemoveCommand 删除客户端实例     
     * @return {@link Result<AcwClientInstance>} 客户端实例     
     
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    @Override
    public Result<AcwClientInstance> remove(AcwClientInstanceRemoveCommand acwClientInstanceRemoveCommand) {
     AcwClientInstance acwClientInstance = AcwClientInstanceDTOAssembler.INSTANCE.toAcwClientInstance(acwClientInstanceRemoveCommand);
     return acwClientInstanceRepository.remove(acwClientInstance);
    }

}