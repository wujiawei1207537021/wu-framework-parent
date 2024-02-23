package com.wu.smart.acw.server.application;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.smart.acw.server.application.command.AcwGenerateLocalJavaCommand;
import com.wu.smart.acw.server.application.command.acw.acw.client.instance.*;
import com.wu.smart.acw.server.application.dto.AcwClientInstanceDTO;
import com.wu.smart.acw.server.domain.model.acw.client.instance.AcwClientInstance;

import java.util.List;

/**
 * describe 客户端实例
 *
 * @author Jia wei Wu
 * @date 2023/12/05 09:32 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface AcwClientInstanceApplication {


    /**
     * describe 新增客户端实例
     *
     * @param acwClientInstanceStoryCommand 新增客户端实例
     * @return {@link Result<AcwClientInstance>} 客户端实例新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> story(AcwClientInstanceStoryCommand acwClientInstanceStoryCommand);

    /**
     * describe 批量新增客户端实例
     *
     * @param acwClientInstanceStoryCommandList 批量新增客户端实例
     * @return {@link Result<List<AcwClientInstance>>} 客户端实例新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<List<AcwClientInstance>> batchStory(List<AcwClientInstanceStoryCommand> acwClientInstanceStoryCommandList);

    /**
     * describe 更新客户端实例
     *
     * @param acwClientInstanceUpdateCommand 更新客户端实例
     * @return {@link Result<AcwClientInstance>} 客户端实例领域对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> updateOne(AcwClientInstanceUpdateCommand acwClientInstanceUpdateCommand);

    /**
     * describe 查询单个客户端实例
     *
     * @param acwClientInstanceQueryOneCommand 查询单个客户端实例
     * @return {@link Result< AcwClientInstanceDTO >} 客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstanceDTO> findOne(AcwClientInstanceQueryOneCommand acwClientInstanceQueryOneCommand);

    /**
     * describe 查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 查询多个客户端实例
     * @return {@link Result <List<AcwClientInstanceDTO>>} 客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<List<AcwClientInstanceDTO>> findList(AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand);

    /**
     * describe 分页查询多个客户端实例
     *
     * @param acwClientInstanceQueryListCommand 分页查询多个客户端实例
     * @return {@link Result <LazyPage<AcwClientInstanceDTO>>} 分页客户端实例DTO对象
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<LazyPage<AcwClientInstanceDTO>> findPage(int size, int current, AcwClientInstanceQueryListCommand acwClientInstanceQueryListCommand);

    /**
     * describe 删除客户端实例
     *
     * @param acwClientInstanceRemoveCommand 删除客户端实例
     * @return {@link Result<AcwClientInstance>} 客户端实例
     * @author Jia wei Wu
     * @date 2023/12/05 09:32 晚上
     **/

    Result<AcwClientInstance> remove(AcwClientInstanceRemoveCommand acwClientInstanceRemoveCommand);

}