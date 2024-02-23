package com.wu.smart.acw.server.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.application.command.acw.client.java.path.*;
import com.wu.smart.acw.server.domain.model.acw.client.java.path.AcwClientJavaPath;
import com.wu.smart.acw.server.application.dto.AcwClientJavaPathDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 05:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface AcwClientJavaPathApplication {


    /**
     * describe 新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommand 新增客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result<AcwClientJavaPath> story(AcwClientJavaPathStoryCommand acwClientJavaPathStoryCommand);

    /**
     * describe 批量新增客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathStoryCommandList 批量新增客户端使用创建Java代码常用路径     
     * @return {@link Result<List<AcwClientJavaPath>>} 客户端使用创建Java代码常用路径新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result<List<AcwClientJavaPath>> batchStory(List<AcwClientJavaPathStoryCommand> acwClientJavaPathStoryCommandList);

    /**
     * describe 更新客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathUpdateCommand 更新客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result<AcwClientJavaPath> updateOne(AcwClientJavaPathUpdateCommand acwClientJavaPathUpdateCommand);

    /**
     * describe 查询单个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryOneCommand 查询单个客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPathDTO>} 客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result<AcwClientJavaPathDTO> findOne(AcwClientJavaPathQueryOneCommand acwClientJavaPathQueryOneCommand);

    /**
     * describe 查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result <List<AcwClientJavaPathDTO>>} 客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result <List<AcwClientJavaPathDTO>> findList(AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand);

    /**
     * describe 分页查询多个客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathQueryListCommand 分页查询多个客户端使用创建Java代码常用路径     
     * @return {@link Result <LazyPage<AcwClientJavaPathDTO>>} 分页客户端使用创建Java代码常用路径DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result <LazyPage<AcwClientJavaPathDTO>> findPage(int size,int current,AcwClientJavaPathQueryListCommand acwClientJavaPathQueryListCommand);

    /**
     * describe 删除客户端使用创建Java代码常用路径
     *
     * @param acwClientJavaPathRemoveCommand 删除客户端使用创建Java代码常用路径     
     * @return {@link Result<AcwClientJavaPath>} 客户端使用创建Java代码常用路径     
     
     * @author Jia wei Wu
     * @date 2023/12/08 05:46 下午
     **/

    Result<AcwClientJavaPath> remove(AcwClientJavaPathRemoveCommand acwClientJavaPathRemoveCommand);

    /**
     * 根据表生成本地Java对应代码
     *
     * @param acwClientGenerateLocalJavaCommand 参数
     * @return Result
     */
    Result<?> clientGenerateLocalJava(AcwClientGenerateLocalJavaCommand acwClientGenerateLocalJavaCommand);
}